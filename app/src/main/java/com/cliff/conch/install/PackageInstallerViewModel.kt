package com.cliff.conch.install

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.ProviderInfo
import android.net.Uri
import android.os.Build
import android.os.RemoteException
import android.os.UserManager
import android.text.TextUtils
import android.util.EventLog
import androidx.lifecycle.ViewModel
import com.cliff.conch.box.util.FileUtil
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import reflect.android.app.ActivityManagerReImpl
import reflect.android.app.ActivityReImpl
import reflect.android.app.IActivityManagerReImpl
import reflect.android.content.pm.ApplicationInfoReImpl
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Arrays
import wrapper.android.content.WIntent as WIntent
import wrapper.android.content.pm.WPackageInstaller.WSessionParams as WSessionParams

// 模拟系统应用PackageInstaller的处理过程
class PackageInstallerViewModel : ViewModel() {
    private var mAbortInstall = false

    // 模拟系统应用PackageInstaller,启动函数, 这里Activity是调用方的activity,为了方便直接写在参数里
    private suspend fun packageInstaller(apkUri: Uri, context: Activity) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        installStart(intent, context)
    }
    
    // 模拟InstallStart (activity)，这里Activity就是InstallStart,为了方便直接写在参数里
    private suspend fun installStart(intent: Intent, context: Activity) {
        val mPackageManager = context.packageManager
        val mUserManager = context.getSystemService(UserManager::class.java)
        var callingPackage = context.callingPackage
        var callingAttributionTag: String? = null
        val isSessionInstall = "android.content.pm.action.CONFIRM_INSTALL" == intent.action
        val sessionId =
            if (isSessionInstall) intent.getIntExtra(PackageInstaller.EXTRA_SESSION_ID, -1) else -1

        if (callingPackage == null && sessionId != -1) {
            val packageInstaller: PackageInstaller = mPackageManager.packageInstaller
            val sessionInfo = packageInstaller.getSessionInfo(sessionId)
            callingPackage = sessionInfo?.installerPackageName
            callingAttributionTag = sessionInfo?.installerAttributionTag
        }

        // 请求方应用信息
        val sourceInfo: ApplicationInfo? = if (callingPackage != null) {
            try {
                mPackageManager.getApplicationInfo(callingPackage, 0)
            } catch (ex: PackageManager.NameNotFoundException) {
                null
            }
        } else null


        // 获取安装来源的uid,并判断是否是可信来源(只有特权应用设置的可信源可以参与判断)
        val originatingUid: Int = originatingUid(sourceInfo, intent, context)
        var isTrustedSource = false
        if (sourceInfo != null && ApplicationInfoReImpl.isPrivilegedApp(sourceInfo)) {
            isTrustedSource = intent.getBooleanExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, false)
        }

        // Android O 以及之上的安装源，必须声明了 android.permission.REQUEST_INSTALL_PACKAGES 权限
        if (!isTrustedSource && originatingUid != WSessionParams.UID_UNKNOWN) {
            val targetSdkVersion: Int =
                PackageUtil.getMaxTargetSdkVersionForUid(context, originatingUid)
            if (targetSdkVersion < 0) {
                Logger.w("Cannot get target sdk version for uid $originatingUid")
                mAbortInstall = true
            } else if (targetSdkVersion >= Build.VERSION_CODES.O &&
                !isUidRequestingPermission(
                    originatingUid,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES,
                    mPackageManager
                )
            ) {
                Logger.e(
                    "Requesting uid " + originatingUid + " needs to declare permission "
                            + Manifest.permission.REQUEST_INSTALL_PACKAGES
                )
                mAbortInstall = true
            }
        }


        // 如果Intent中存在EXTRA_INSTALLER_PACKAGE_NAME，则比较启动package是否与之相同，如果不同则判断callingPkgName是否拥有安装包权限
        val installerPackageNameFromIntent =
            intent.getStringExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME)
        if (installerPackageNameFromIntent != null) {
            val callingPkgName = ActivityReImpl.getLaunchedFromPackage(context)
            if (callingPkgName.isNullOrBlank()) {
                Logger.e("getLaunchedFromPackage is:${callingPkgName ?: "NULL"}")
            } else if (!TextUtils.equals(installerPackageNameFromIntent, callingPkgName)
                && mPackageManager.checkPermission(
                    Manifest.permission.INSTALL_PACKAGES,
                    callingPkgName
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Logger.e("The given installer package name $installerPackageNameFromIntent is invalid. Remove it.")
                EventLog.writeEvent(
                    0x534e4554,
                    "236687884",
                    ActivityReImpl.getLaunchedFromUid(context),
                    "Invalid EXTRA_INSTALLER_PACKAGE_NAME"
                )
                intent.removeExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME)
            }
        }

        if (mAbortInstall) {
            // 取消安装，设置失败
            return
        }


        var nextActivity = Intent(intent).apply {
            setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(EXTRA_CALLING_PACKAGE, callingPackage)
            putExtra(EXTRA_CALLING_ATTRIBUTION_TAG, callingAttributionTag)
            putExtra(EXTRA_ORIGINAL_SOURCE_INFO, sourceInfo)
            putExtra(WIntent.EXTRA_ORIGINATING_UID, originatingUid)
        }

        if (isSessionInstall) {
            // 进入PackageInstallerActivityActivity
            // nextActivity.setClass(context, PackageInstallerActivity::class.java)
            Install.packageInstallerActivity(nextActivity, context)
        } else {
            val packageUri = intent.data
            if (packageUri != null && packageUri.scheme == ContentResolver.SCHEME_CONTENT) {
                // 进入InstallStaging
                // nextActivity.setClass(this, InstallStaging::class.java)
                installStaging(nextActivity, context)
            } else if (packageUri != null && packageUri.scheme == SCHEME_PACKAGE) {
                //                进入PackageInstallerActivityActivity
                //                nextActivity.setClass(this, PackageInstallerActivity::class.java)
                Install.packageInstallerActivity(nextActivity, context)
            } else {
                //                安装失败
                //                val result = Intent()
                //                result.putExtra(WIntent.EXTRA_INSTALL_RESULT,-3)
                //                setResult(Activity.RESULT_FIRST_USER, result)
                //                nextActivity = null
            }
        }
    }

    //模拟 InstallStaging (activity) 这里的context表示InstallStaging这个Activity自己，为简略作为参数
    // 仅仅是文件拷贝的作用
    private suspend fun installStaging(intent: Intent, context: Activity) {
        val packageUri = intent.data ?: return
        val mStagedFile = FileUtil.childrenAppExternalCache()
        withContext(Dispatchers.IO) {
            val success = try {
                context.contentResolver.openInputStream(packageUri).use { inputStream ->
                    if (inputStream == null)
                        return@withContext
                    FileOutputStream(mStagedFile).use { outputStream ->
                        val buffer = ByteArray(1024 * 1024)
                        var bytesRead: Int
                        while ((inputStream.read(buffer).also { bytesRead = it }) >= 0) {
                            outputStream.write(buffer, 0, bytesRead)
                        }
                    }
                    true
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> false
                    is SecurityException -> false
                    is IllegalStateException -> false
                    else -> true
                }
            }

            if (success) {
                withContext(Dispatchers.Main) {
                    val installIntent = Intent(intent)
                    installIntent.setData(Uri.fromFile(mStagedFile))
                    if (installIntent.getBooleanExtra(Intent.EXTRA_RETURN_RESULT, false)) {
                        installIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                    }
                    installIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    deleteStagedFileOnResult(installIntent, context)
                }
            }
        }
    }

    // 模拟 DeleteStagedFileOnResult (activity)
    private suspend fun deleteStagedFileOnResult(intent: Intent, activity: Activity) {
        val installIntent: Intent = Intent(intent)
//        installIntent.setClass(this, PackageInstallerActivity::class.java)
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        Install.packageInstallerActivity(installIntent, activity)
        // 删除安装包
        withContext(Dispatchers.IO) {
            intent.data?.path?.let {
                File(it).delete()
            }
        }
//        startActivityForResult(installIntent, 0)
    }



    // 模拟 InstallInstalling （activity）
    private suspend fun installInstalling() {
        val file = File.createTempFile("pacakgeName", ".apk", File(""))
        file.deleteOnExit()
    }

    // 获取安装来源的uid
    private fun originatingUid(sourceInfo: ApplicationInfo?, intent: Intent, context: Activity): Int {
        val uidFromIntent: Int = intent.getIntExtra(
            wrapper.android.content.WIntent.EXTRA_ORIGINATING_UID,
            WSessionParams.UID_UNKNOWN
        )

        val callingUid = sourceInfo?.uid ?: try {
            val iActivityManager = ActivityManagerReImpl.getService()!!
            val iToken = ActivityReImpl.getActivityToken(context)
            IActivityManagerReImpl.getLaunchedFromUid(iActivityManager, iToken)
        } catch (ex: RemoteException) {
            // Cannot reach ActivityManager. Aborting install.
            Logger.e("Could not determine the launching uid.")
            mAbortInstall = true
            WSessionParams.UID_UNKNOWN
        }


        val permission =
            context.checkPermission(Manifest.permission.MANAGE_DOCUMENTS, -1, callingUid)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return uidFromIntent
        }
        if (isSystemDownloadsProvider(callingUid, context)) {
            return uidFromIntent
        }
        return callingUid
    }

    // 判断系统下载Provider
    private fun isSystemDownloadsProvider(uid: Int, context: Context): Boolean {
        val downloadProviderPackage: ProviderInfo? = context.packageManager.resolveContentProvider(
            InstallViewModel.DOWNLOADS_AUTHORITY, 0
        )
        if (downloadProviderPackage == null) {
            return false
        }
        val appInfo = downloadProviderPackage.applicationInfo
        return (ApplicationInfoReImpl.isSystemApp(appInfo) && uid == appInfo.uid)
    }


    private fun isUidRequestingPermission(
        uid: Int,
        permission: String,
        packageManager: PackageManager
    ): Boolean {
        val packageNames: Array<String> = packageManager.getPackagesForUid(uid) ?: return false
        for (packageName in packageNames) {
            val packageInfo: PackageInfo
            try {
                packageInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_PERMISSIONS
                )
            } catch (e: PackageManager.NameNotFoundException) {
                // Ignore and try the next package
                continue
            }
            if (packageInfo.requestedPermissions != null
                && Arrays.asList(*packageInfo.requestedPermissions).contains(permission)
            ) {
                return true
            }
        }
        return false
    }




    companion object {
        const val REQUEST_TRUST_EXTERNAL_SOURCE: Int = 1
        const val SCHEME_PACKAGE: String = "package"
        const val EXTRA_CALLING_PACKAGE: String = "EXTRA_CALLING_PACKAGE"
        const val EXTRA_CALLING_ATTRIBUTION_TAG: String = "EXTRA_CALLING_ATTRIBUTION_TAG"
        const val EXTRA_ORIGINAL_SOURCE_INFO: String = "EXTRA_ORIGINAL_SOURCE_INFO"
        val ALLOW_UNKNOWN_SOURCES_KEY: String =
            PackageInstallerViewModel::class.java.getName() + "ALLOW_UNKNOWN_SOURCES_KEY"
    }
}
