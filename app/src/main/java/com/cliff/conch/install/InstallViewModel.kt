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
import com.cliff.conch.box.HostApkInfo
import com.cliff.conch.box.util.FileUtil
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import reflect.android.app.ActivityThread
import reflect.android.app.ActivityThread__Functions.getSPackageManager
import reflect.android.content.pm.PackageInstaller.SessionParams
import reflect.android.content.pm.PackageInstaller_SessionParams__Functions.__instance__
import reflect.android.content.pm.parsing.ApkLiteParseUtils
import reflect.android.content.pm.parsing.ApkLiteParseUtils__Functions.parsePackageLite
import reflect.android.content.pm.parsing.result.ParseTypeImpl
import reflect.android.content.pm.parsing.result.ParseTypeImpl__Functions.__instance__
import reflect.android.content.pm.parsing.result.ParseTypeImpl__Functions.forDefaultParsing
import java.io.File
import java.io.FileInputStream
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
//            normalInstallApk()
            loadedApkInstall()
        }
    }

    private suspend fun loadedApkInstall() {
        withContext(Dispatchers.IO) {
            val cacheApk = FileUtil.childCache()
            if (!cacheApk.exists() || cacheApk.length() == 0L) {
                context.assets.open("Now.apk").use { inputStream ->
                    FileOutputStream(cacheApk).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
            val packageInfo =
                context.packageManager.getPackageArchiveInfo(cacheApk.absolutePath, 0)!!
            val packageName = packageInfo.packageName
            val baseApk = FileUtil.childAppBaseFile(packageName)
            if (!baseApk.exists() || baseApk.length() == 0L) {
                FileInputStream(cacheApk).use { inputStream ->
                    FileOutputStream(baseApk).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
            cacheApk.delete()
            baseApk.setReadOnly()
            Logger.i("LoadedApk 开始加载")
            val loadedApk = HostApkInfo.loadApplication(baseApk.absolutePath)
            Logger.i("LoadedApk 加载完成")
            withContext(Dispatchers.Main) {
                val intent = Intent().apply {
                    setComponent(nowWindComponentName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                Logger.i("LoadedApk 加载完成，启动Activity")
                context.startActivity(intent)
            }
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
    private suspend fun normalInstallApk(apk: File, intent: Intent) {
        withContext(Dispatchers.IO) {
            val params = PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL
            )
            val referrerUri: Uri? = intent.getParcelableExtra(Intent.EXTRA_REFERRER)
            params.setPackageSource(
                if (referrerUri != null) PackageInstaller.PACKAGE_SOURCE_DOWNLOADED_FILE
                else PackageInstaller.PACKAGE_SOURCE_LOCAL_FILE
            )
            SessionParams.__instance__(params).setInstallAsInstantApp(false)
            //        params.setInstallAsInstantApp(false)
            params.setReferrerUri(referrerUri)
            params.setOriginatingUri(intent.getParcelableExtra(Intent.EXTRA_ORIGINATING_URI))
            params.setOriginatingUid(intent.getIntExtra("android.intent.extra.ORIGINATING_UID", -1))
            params.setInstallerPackageName(intent.getStringExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME))
            params.setInstallReason(PackageManager.INSTALL_REASON_USER)

            // ParseTypeImpl
            val input = ParseTypeImpl.forDefaultParsing()
            // ParseResult<PackageLite>
            val result =
                ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.__instance__(input).reset(), apk, 0)

        }
    }

    private suspend fun installApk(apk: File) {
        withContext(Dispatchers.IO) {
            //PackageManager
            val mPm = context.packageManager
            //IPackageManager
            val mIpm = ActivityThread.getSPackageManager()
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
        const val nowWindPkgName = "com.google.samples.apps.nowinandroid"
        const val nowWindActivityName = "com.google.samples.apps.nowinandroid.MainActivity"
        val nowWindComponentName = ComponentName(nowWindPkgName, nowWindActivityName)
        const val DOWNLOADS_AUTHORITY: String = "downloads"
    }


}