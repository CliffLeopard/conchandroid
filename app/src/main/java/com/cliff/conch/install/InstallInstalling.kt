package com.cliff.conch.install

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.net.Uri
import com.orhanobut.logger.Logger
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import reflect.android.content.pm.PackageInstaller_SessionParams__Functions.__instance__
import reflect.android.content.pm.PackageInstaller_Session__Functions.__instance__
import reflect.android.content.pm.PackageManager__Functions.__instance__
import reflect.android.content.pm.parsing.ApkLiteParseUtils__Functions.parsePackageLite
import reflect.android.content.pm.parsing.PackageLite__Functions.__instance__
import reflect.android.content.pm.parsing.result.ParseResult__Functions.__instance__
import reflect.android.content.pm.parsing.result.ParseTypeImpl__Functions.__instance__
import reflect.android.content.pm.parsing.result.ParseTypeImpl__Functions.forDefaultParsing
import reflect.com.android.internal.content.InstallLocationUtils__Functions.calculateInstalledSize
import reflect.android.content.pm.PackageManager as PackageManagerReImpl
import reflect.android.content.pm.PackageInstaller.SessionParams as SessionParamsReImpl
import reflect.android.content.pm.PackageInstaller.Session as SessionReImpl
import reflect.android.content.pm.parsing.ApkLiteParseUtils as ApkLiteParseUtilsReImpl
import reflect.android.content.pm.parsing.PackageLite as PackageLiteReImpl
import reflect.android.content.pm.parsing.result.ParseResult as ParseResultReImpl
import reflect.android.content.pm.parsing.result.ParseTypeImpl as ParseTypeImplReImpl
import reflect.com.android.internal.content.InstallLocationUtils as InstallLocationUtilsReImpl
import wrapper.android.content.WIntent
import wrapper.android.content.pm.WPackageInstaller
import wrapper.android.content.pm.WPackageManager
import java.io.File
import java.io.FileInputStream
import java.io.IOException

object InstallInstalling {
    const val SESSION_ID: String = "com.android.packageinstaller.SESSION_ID"
    const val INSTALL_ID: String = "com.android.packageinstaller.INSTALL_ID"
    private const val BROADCAST_ACTION: String =
        "com.android.packageinstaller.ACTION_INSTALL_COMMIT"
    private var mSessionId = 0
    private var mInstallId = 0
    private lateinit var mPackageURI: Uri
    @OptIn(DelicateCoroutinesApi::class)
    fun begin(intent: Intent, context: Activity) {
        val appInfo: ApplicationInfo =
            intent.getParcelableExtra(PackageUtil.INTENT_ATTR_APPLICATION_INFO)!!
        mPackageURI = intent.data!!
        val mPm = context.packageManager
        if ("package" == mPackageURI.scheme) {
            try {
                PackageManagerReImpl.__instance__(mPm).installExistingPackage(appInfo.packageName)
//                mPm.installExistingPackage(appInfo.packageName)
                launchSuccess(intent)
            } catch (e: PackageManager.NameNotFoundException) {
                launchFailure(
                    PackageInstaller.STATUS_FAILURE,
                    WPackageManager.INSTALL_FAILED_INTERNAL_ERROR, null,
                    intent,
                    context
                )
            }
        } else {
            val params =
                PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
            val referrerUri: Uri? = intent.getParcelableExtra(Intent.EXTRA_REFERRER)
            params.setPackageSource(
                if (referrerUri != null) PackageInstaller.PACKAGE_SOURCE_DOWNLOADED_FILE
                else PackageInstaller.PACKAGE_SOURCE_LOCAL_FILE
            )
            SessionParamsReImpl.__instance__(params).setInstallAsInstantApp(false)
//            params.setInstallAsInstantApp(false)
            params.setReferrerUri(referrerUri)
            params.setOriginatingUri(intent.getParcelableExtra(Intent.EXTRA_ORIGINATING_URI))
            params.setOriginatingUid(
                intent.getIntExtra(
                    WIntent.EXTRA_ORIGINATING_UID,
                    WPackageInstaller.WSessionParams.UID_UNKNOWN
                )
            )
            params.setInstallerPackageName(intent.getStringExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME))
            params.setInstallReason(PackageManager.INSTALL_REASON_USER)

            val file = File(mPackageURI.path!!)
            try {
                val input = ParseTypeImplReImpl.forDefaultParsing()
                val result = ApkLiteParseUtilsReImpl.parsePackageLite(
                    ParseTypeImplReImpl.__instance__(input).reset(),
                    file,
                    0
                )
                if (ParseResultReImpl.__instance__(result).isError()) {
                    Logger.e(
                        "Cannot parse package $file. Assuming defaults."
                    )
                    Logger.e(
                        "Cannot calculate installed size $file. Try only apk size."
                    )
                    params.setSize(file.length())
                } else {
                    val pkg = ParseResultReImpl.__instance__(result).getResult()
                    val pkgImpl = PackageLiteReImpl.__instance__(pkg)
                    params.setAppPackageName(pkgImpl.getPackageName())
                    params.setInstallLocation(pkgImpl.getInstallLocation())
                    params.setSize(
                        InstallLocationUtilsReImpl.calculateInstalledSize(
                            pkg,
                            SessionParamsReImpl.__instance__(params).abiOverride
                        )
                    )
                }
            } catch (e: IOException) {
                Logger.e("Cannot calculate installed size $file. Try only apk size.")
                params.setSize(file.length())
            }

//            try {
//                mInstallId = InstallEventReceiver
//                    .addObserver(
//                        this, EventResultPersister.GENERATE_NEW_ID
//                    ) { statusCode: Int, legacyStatus: Int, statusMessage: String? ->
//                        this.launchFinishBasedOnResult(
//                            statusCode,
//                            legacyStatus,
//                            statusMessage
//                        )
//                    }
//            } catch (e: EventResultPersister.OutOfIdsException) {
//                launchFailure(
//                    PackageInstaller.STATUS_FAILURE,
//                    WPackageManager.INSTALL_FAILED_INTERNAL_ERROR, null,
//                    intent,context
//                )
//            }

            try {
                mSessionId = mPm.packageInstaller.createSession(params)
            } catch (e: IOException) {
                launchFailure(
                    PackageInstaller.STATUS_FAILURE,
                    WPackageManager.INSTALL_FAILED_INTERNAL_ERROR, null,
                    intent, context
                )
            }
        }

        GlobalScope.launch {
            installTask(context, intent)
        }
        //
    }

    private suspend fun installTask(context: Activity, intent: Intent) {
        val installer = context.packageManager.packageInstaller
        val sessionInfo = installer.getSessionInfo(mSessionId)
        if (sessionInfo != null && !sessionInfo.isActive) {
            withContext(Dispatchers.IO) {
                var session = try {
                    installer.openSession(mSessionId)
                } catch (_: IOException) {
                    null
                }
                if (session == null) {
                    Logger.e("install error:io exception")
                    return@withContext
                }

                session.setStagingProgress(0F)
                try {
                    val file = File(mPackageURI.path!!)
                    FileInputStream(file).use { inputStream ->
                        val sizeBytes = file.length()
                        session!!.openWrite("PackageInstaller", 0, sizeBytes).use { outputStream ->
                            val buffer = ByteArray(1024 * 1024)
                            while (true) {
                                val numRead: Int = inputStream.read(buffer)
                                if (numRead == -1) {
                                    session!!.fsync(outputStream)
                                    break
                                }

                                outputStream.write(buffer, 0, numRead)
                                if (sizeBytes > 0) {
                                    val fraction = (numRead.toFloat() / sizeBytes.toFloat())
                                    SessionReImpl.__instance__(session!!).addProgress(fraction)
//                                    session.addProgress(fraction)
                                }
                            }
                        }
                    }
                } catch (_: Exception) {
                    session.close()
                    session = null
                }

                withContext(Dispatchers.Main) {
                    if (session != null) {
                        val broadcastIntent = Intent(BROADCAST_ACTION)
                        broadcastIntent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                        broadcastIntent.setPackage(context.packageName)
//                        broadcastIntent.putExtra(EventResultPersister.EXTRA_ID, mInstallId)

                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            mInstallId,
                            broadcastIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                        )

                        session.commit(pendingIntent.intentSender)
//                        mCancelButton.setEnabled(false)
//                        setFinishOnTouchOutside(false)
                    }

                }
            }
        }
    }


    // 启动安装成功actvity
    private fun launchSuccess(intent: Intent) {
        val successIntent: Intent = Intent(intent)
//        successIntent.setClass(this, InstallSuccess::class.java)
        successIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
//        startActivity(successIntent)
//        finish()
    }

    // 启动FailureActivity
    private fun launchFailure(
        statusCode: Int,
        legacyStatus: Int,
        statusMessage: String?,
        intent: Intent,
        context: Activity
    ) {
        val failureIntent: Intent = Intent(intent)
//        failureIntent.setClass(context, InstallFailed::class.java)
        failureIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
        failureIntent.putExtra(PackageInstaller.EXTRA_STATUS, statusCode)
        failureIntent.putExtra(WPackageInstaller.EXTRA_LEGACY_STATUS, legacyStatus)
        failureIntent.putExtra(PackageInstaller.EXTRA_STATUS_MESSAGE, statusMessage)
        // 启动安装失败activity
    }
}