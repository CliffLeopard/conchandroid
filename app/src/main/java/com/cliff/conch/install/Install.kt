package com.cliff.conch.install

import android.app.Activity
import android.app.AppOpsManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.net.Uri
import android.os.UserManager
import com.cliff.conch.install.PackageInstallerViewModel.Companion.EXTRA_CALLING_ATTRIBUTION_TAG
import com.cliff.conch.install.PackageInstallerViewModel.Companion.EXTRA_CALLING_PACKAGE
import com.cliff.conch.install.PackageInstallerViewModel.Companion.EXTRA_ORIGINAL_SOURCE_INFO
import com.orhanobut.logger.Logger
import reflect.android.app.AppGlobalsReImpl
import reflect.android.content.pm.PackageInstallerReImpl
import reflect.android.content.pm.SessionInfoReImpl
import java.io.File
import kotlin.properties.Delegates
import wrapper.android.content.WIntent as WIntent
import wrapper.android.content.pm.WPackageInstaller as WPackageInstaller
import wrapper.android.content.pm.WPackageInstaller.WSessionParams as WSessionParams

object Install {
    private var mPackageURI: Uri? = null
    private var mOriginatingURI: Uri? = null
    private var mReferrerURI: Uri? = null
    private var mPkgInfo: PackageInfo? = null
    private lateinit var mPm: PackageManager
    private var mSessionId = -1
    private lateinit var mInstaller: PackageInstaller
    private var mOriginatingUid by Delegates.notNull<Int>()

    // 模拟 PackageInstallerActivity
    suspend fun packageInstallerActivity(intent: Intent, context: Activity) {
        mPm = context.packageManager
        val mIpm = AppGlobalsReImpl.getPackageManager()
        val mAppOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        mInstaller = mPm.packageInstaller
        val mUserManager = context.getSystemService(Context.USER_SERVICE) as UserManager

        val mCallingPackage =
            intent.getStringExtra(EXTRA_CALLING_PACKAGE)
        val mCallingAttributionTag =
            intent.getStringExtra(EXTRA_CALLING_ATTRIBUTION_TAG)
        val mSourceInfo =
            intent.getParcelableExtra<ApplicationInfo>(EXTRA_ORIGINAL_SOURCE_INFO)
        mOriginatingUid = intent.getIntExtra(
            wrapper.android.content.WIntent.EXTRA_ORIGINATING_UID,
            WSessionParams.UID_UNKNOWN
        )
        val mOriginatingPackage = if ((mOriginatingUid != WSessionParams.UID_UNKNOWN)
        ) getPackageNameForUid(mOriginatingUid, mPm, mCallingPackage) else null


        val packageUri: Uri?
        if (WPackageInstaller.ACTION_CONFIRM_INSTALL == intent.action) {
            val sessionId =
                intent.getIntExtra(android.content.pm.PackageInstaller.EXTRA_SESSION_ID, -1)
            val info = mInstaller.getSessionInfo(sessionId)
            if (info == null || !SessionInfoReImpl.sealed_o_get_(info) || SessionInfoReImpl.resolvedBaseCodePath_o_get_(
                    info
                ) == null
            ) {
                Logger.w(
                    "Session $sessionId in funky state; ignoring"
                )
                return
            }

            mSessionId = sessionId
            val baseCodePath = SessionInfoReImpl.resolvedBaseCodePath_o_get_(info)!!
            packageUri = Uri.fromFile(File(baseCodePath))
            mOriginatingURI = null
            mReferrerURI = null
        } else {
            mSessionId = -1
            packageUri = intent.data
            mOriginatingURI = intent.getParcelableExtra<Uri>(Intent.EXTRA_ORIGINATING_URI)
            mReferrerURI = intent.getParcelableExtra<Uri>(Intent.EXTRA_REFERRER)
        }

        // if there's nothing to do, quietly slip into the ether
        if (packageUri == null) {
            Logger.w("Unspecified source")
            return
        }

        val wasSetUp = processPackageUri(packageUri, context, intent)
        if (!wasSetUp) {
            Logger.w("Install failed:wasNotSetUp")
        }
    }

    private fun getPackageNameForUid(
        sourceUid: Int,
        mPm: PackageManager,
        mCallingPackage: String?
    ): String? {
        val packagesForUid: Array<String> = mPm.getPackagesForUid(sourceUid) ?: return null
        if (packagesForUid.size > 1) {
            if (mCallingPackage != null) {
                for (packageName in packagesForUid) {
                    if (packageName == mCallingPackage) {
                        return packageName
                    }
                }
            }
            Logger.i("Multiple packages found for source uid $sourceUid")
        }
        return packagesForUid[0]
    }

    private suspend fun processPackageUri(
        packageUri: Uri,
        context: Activity,
        intent: Intent
    ): Boolean {
        mPackageURI = packageUri
        val scheme = packageUri.scheme
        Logger.i("processPackageUri(): uri=$packageUri, scheme=$scheme")
        when (scheme) {
            PackageInstallerViewModel.SCHEME_PACKAGE -> {
                try {
                    mPkgInfo = mPm.getPackageInfo(
                        packageUri.schemeSpecificPart,
                        PackageManager.GET_PERMISSIONS or PackageManager.MATCH_UNINSTALLED_PACKAGES
                    )
                } catch (_: PackageManager.NameNotFoundException) {
                }
                if (mPkgInfo == null) {
                    Logger.w("Requested package ${packageUri.scheme} not available. Discontinuing installation")
                    return false
                }
                val label: CharSequence = mPm.getApplicationLabel(mPkgInfo!!.applicationInfo)
                Logger.i("creating snippet for $label")
                clickOk(intent, context)
            }

            ContentResolver.SCHEME_FILE -> {
                val sourceFile = File(packageUri.path!!)
                mPkgInfo = PackageUtil.getPackageInfo(
                    context, sourceFile,
                    PackageManager.GET_PERMISSIONS
                )
                if (mPkgInfo == null) {
                    Logger.w("Parse error when parsing manifest. Discontinuing installation")
                    return false
                }
                Logger.i("creating snippet for local file $sourceFile")
                clickOk(intent, context)
            }

            else -> {
                throw IllegalArgumentException("Unexpected URI scheme $packageUri")
            }
        }
        return true
    }

    private suspend fun clickOk(intent: Intent, context: Activity) {
        if (mSessionId != -1) {
            PackageInstallerReImpl.setPermissionsResult(mInstaller, mSessionId, true)
//            mInstaller.setPermissionsResult(mSessionId, true)
//            finish()
        } else {
            startInstall(intent, context)
        }
    }

    private suspend fun startInstall(intent: Intent, context: Activity) {
        // Start subactivity to actually install the application
        val newIntent = Intent().apply {
            putExtra(
                PackageUtil.INTENT_ATTR_APPLICATION_INFO,
                mPkgInfo!!.applicationInfo
            )
            setData(mPackageURI)
        }

        val installerPackageName: String? =
            intent.getStringExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME)
        if (mOriginatingURI != null) {
            newIntent.putExtra(Intent.EXTRA_ORIGINATING_URI, mOriginatingURI)
        }
        if (mReferrerURI != null) {
            newIntent.putExtra(Intent.EXTRA_REFERRER, mReferrerURI)
        }
        if (mOriginatingUid != WSessionParams.UID_UNKNOWN) {
            newIntent.putExtra(WIntent.EXTRA_ORIGINATING_UID, mOriginatingUid)
        }
        if (installerPackageName != null) {
            newIntent.putExtra(
                Intent.EXTRA_INSTALLER_PACKAGE_NAME,
                installerPackageName
            )
        }
        if (intent.getBooleanExtra(Intent.EXTRA_RETURN_RESULT, false)) {
            newIntent.putExtra(Intent.EXTRA_RETURN_RESULT, true)
        }
        newIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
        Logger.i("downloaded app uri=$mPackageURI")
        InstallInstalling.begin(intent, context)
//        startActivity(newIntent)
//        finish()
    }
}