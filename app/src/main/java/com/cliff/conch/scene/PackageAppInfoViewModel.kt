package com.cliff.conch.scene

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.conch.ConchApplication
import com.cliff.conch.box.util.FileUtil
import com.cliff.conch.install.InstallViewModel
import com.orhanobut.logger.Logger
import dalvik.system.PathClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import reflect.android.app.ActivityThreadReImpl
import reflect.android.app.LoadedApkReImpl
import reflect.android.view.DisplayAdjustmentsReImpl
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class PackageAppInfoViewModel : ViewModel() {
    private lateinit var apkPath: String
    private lateinit var pkgInfo: PackageInfo
    fun getLoadedApkInfo() {
        viewModelScope.launch {
            prepare()
            packageInfo()
            loadedApk()
        }
    }


    private suspend fun loadedApk() {
        withContext(Dispatchers.IO) {
            val applicationInfo = pkgInfo.applicationInfo
            Logger.i("ApplicationInfo:",applicationInfo)

            val activityThread = ActivityThreadReImpl.currentActivityThread()!!

            val classLoader =
                PathClassLoader(apkPath, PackageAppInfoViewModel::class.java.classLoader!!.parent)
            val mPackages = ActivityThreadReImpl.mPackages_o_get_(activityThread)
            val nowLoadedApk = mPackages[ConchApplication.context.packageName]!!.get()!!
            val mDisplayAdjustments = LoadedApkReImpl.mDisplayAdjustments_o_get_(nowLoadedApk)
            val mCompatInfo = DisplayAdjustmentsReImpl.mCompatInfo_o_get_(mDisplayAdjustments)

            val newLoadedApk = ActivityThreadReImpl.getPackageInfo(
                activityThread,
                applicationInfo,
                mCompatInfo,
                classLoader,
                false,
                false,
                false,
                false
            )
            mPackages[InstallViewModel.nowWindPkgName] = WeakReference(newLoadedApk)
            withContext(Dispatchers.Main) {
                val intent = Intent().apply {
                    setComponent(InstallViewModel.nowWindComponentName)
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                ConchApplication.context.startActivity(intent)
            }
        }
    }

    private suspend fun packageInfo() {
        withContext(Dispatchers.IO) {
            pkgInfo = ConchApplication.context.packageManager.getPackageArchiveInfo(
                apkPath,
                PackageManager.GET_ACTIVITIES
            )!!
        }
    }

    private suspend fun prepare() {
        withContext(Dispatchers.IO) {
            val apkFile = File(FileUtil.childrenCacheDir(), "temp.apk")
            if (!apkFile.exists()) {
                ConchApplication.context.assets.open("Now.apk").use { inputStream ->
                    FileOutputStream(apkFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
            apkFile.setReadOnly()
            apkPath = apkFile.absolutePath
        }
    }
}