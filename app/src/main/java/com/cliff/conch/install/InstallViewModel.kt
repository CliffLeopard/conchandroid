package com.cliff.conch.install

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.UserManager
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.conch.ConchApplication
import com.cliff.conch.box.util.FileUtil
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import reflect.android.app.ActivityThreadReImpl
import reflect.android.content.pm.SessionParamsReImpl
import reflect.android.content.pm.parsing.ApkLiteParseUtilsReImpl
import reflect.android.content.pm.parsing.result.ParseTypeImplReImpl
import java.io.File
import java.io.FileOutputStream

//@HiltViewModel
class InstallViewModel : ViewModel() {
    val apps: LiveData<MutableList<AppItem>> get() = AppItem.installedApps
    private val fileAuthor = "com.cliff.conch.fileprovider"

    @SuppressLint("StaticFieldLeak")
    private val context = ConchApplication.context
    fun install() {
        viewModelScope.launch {
//            scheduleApk()
            normalInstallApk()
        }
    }

    private suspend fun scheduleApk() {
        withContext(Dispatchers.IO) {
            val cacheApk = FileUtil.childCache()
            if (!cacheApk.exists()) {
                context.assets.open("Now.apk").use { inputStream ->
                    FileOutputStream(cacheApk).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
            installApk(cacheApk)
        }
    }

    private suspend fun normalInstallApk() {
        withContext(Dispatchers.IO) {
            val uri = apkProviderUri()
            openInstallApkActivity(uri)
        }
    }

    private suspend fun apkProviderUri(): Uri {
        return withContext(Dispatchers.IO) {
            val externalCacheApk = FileUtil.childrenAppExternalCache()
            if (!externalCacheApk.exists()) {
                context.assets.open("Now.apk").use { inputStream ->
                    FileOutputStream(externalCacheApk).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
            FileProvider.getUriForFile(context, fileAuthor, externalCacheApk)
        }
    }

    @SuppressWarnings("unused")
    private suspend fun openInstallApkActivity(apkUri: Uri) {
        withContext(Dispatchers.IO) {
            Logger.i("apkUri:${apkUri}")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            context.startActivity(intent)
        }
    }

    private fun launchInstallerApk(name: ComponentName) {
        val intent = Intent.makeMainActivity(name)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private suspend fun normalInstallApk(apk:File,intent: Intent) {
        withContext(Dispatchers.IO) {
            val params = PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL
            )
            val referrerUri: Uri? = intent.getParcelableExtra(Intent.EXTRA_REFERRER)
            params.setPackageSource(
                if (referrerUri != null) PackageInstaller.PACKAGE_SOURCE_DOWNLOADED_FILE
                else PackageInstaller.PACKAGE_SOURCE_LOCAL_FILE
            )
            SessionParamsReImpl.setInstallAsInstantApp(params,false)
            //        params.setInstallAsInstantApp(false)
            params.setReferrerUri(referrerUri)
            params.setOriginatingUri(intent.getParcelableExtra(Intent.EXTRA_ORIGINATING_URI))
            params.setOriginatingUid(intent.getIntExtra("android.intent.extra.ORIGINATING_UID",-1))
            params.setInstallerPackageName(intent.getStringExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME))
            params.setInstallReason(PackageManager.INSTALL_REASON_USER)

            // ParseTypeImpl
            val input = ParseTypeImplReImpl.forDefaultParsing()
            // ParseResult<PackageLite>
            val result = ApkLiteParseUtilsReImpl.parsePackageLite(ParseTypeImplReImpl.reset(input),apk,0)

        }
    }

    private suspend fun installApk(apk: File) {
        withContext(Dispatchers.IO) {
            //PackageManager
            val mPm = context.packageManager
            //IPackageManager
            val mIpm = ActivityThreadReImpl.sPackageManager_s_get_()
            // AppOpsManager
            val mAppOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            // PackageInstaller
            val mInstaller = mPm.packageInstaller
            // UserManager
            val mUserManager = context.getSystemService(Context.USER_SERVICE) as UserManager

//            val mPkgInfo = mPm.getPackageInfo(
//                packageUri.schemeSpecificPart,
//                PackageManager.GET_PERMISSIONS or PackageManager.MATCH_UNINSTALLED_PACKAGES
//            )

            val mPkgInfo = mPm.getPackageArchiveInfo(apk.absolutePath, 0)
            Logger.i(mPkgInfo?.packageName ?: "NULL")
        }
    }

    companion object {
        private const val nowWindPkgName = "com.google.samples.apps.nowinandroid"
        private const val nowWindActivityName = "com.google.samples.apps.nowinandroid.MainActivity"
        val nowWindComponentName = ComponentName(nowWindPkgName, nowWindActivityName)
    }

}