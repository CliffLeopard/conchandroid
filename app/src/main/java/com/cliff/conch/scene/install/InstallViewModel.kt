package com.cliff.conch.scene.install

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.UserManager
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.conch.ConchApplication
import com.cliff.conch.reflect.PActivityThreadImpl
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class InstallViewModel : ViewModel() {
    val apps: LiveData<MutableList<AppItem>> get() = AppItem.installedApps
    private val fileAuthor = "com.cliff.conch.fileprovider"

    @SuppressLint("StaticFieldLeak")
    private val context = ConchApplication.context
    fun install() {
        viewModelScope.launch {
            scheduleApk()
        }
    }

    private suspend fun scheduleApk() {
        withContext(Dispatchers.IO) {
            val apkPath = context.getExternalFilesDir("apk")
            val nowApk = File(apkPath, "Now.apk")
            if (!nowApk.exists()) {
                context.assets.open("Now.apk").use { inputStream ->
                    FileOutputStream(nowApk).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
            val apkUri = FileProvider.getUriForFile(context, fileAuthor, nowApk)
            installApk(apkUri,nowApk)
//            openInstallApkActivity(apkUri)
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

    private suspend fun installApk(packageUri: Uri, apk:File) {
        withContext(Dispatchers.IO) {
            //PackageManager
            val mPm = context.packageManager
            //IPackageManager
            val mIpm = PActivityThreadImpl.sPackageManager_s_get_()
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

            val mPkgInfo = mPm.getPackageArchiveInfo(apk.absolutePath,0)
            Logger.i(mPkgInfo?.packageName ?:"NULL")
        }
    }

}