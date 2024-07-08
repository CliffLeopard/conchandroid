package com.cliff.conch.box

import android.content.pm.ApplicationInfo
import android.util.ArrayMap
import com.cliff.conch.ConchApplication
import com.orhanobut.logger.Logger
import dalvik.system.PathClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import reflect.android.app.ActivityThreadReImpl
import reflect.android.app.LoadedApkReImpl
import reflect.android.view.DisplayAdjustmentsReImpl
import java.lang.ref.WeakReference

object HostApkInfo {
    val context by lazy {
        ConchApplication.context
    }

    private val activityThread by lazy {
        ActivityThreadReImpl.currentActivityThread()!!
    }

    private val mPackages: ArrayMap<String, WeakReference<Any>> by lazy {
        ActivityThreadReImpl.mPackages_o_get_(activityThread)
    }

    //    var packageInfo: LoadedApk? = null
    private val packageInfo: Any by lazy {
        mPackages[context.packageName]!!.get()!!
    }

    private val applicationInfo: ApplicationInfo by lazy {
        LoadedApkReImpl.mApplicationInfo_o_get_(packageInfo)
    }

    private val compatInfo: Any by lazy {
        val displayAdjustments = LoadedApkReImpl.mDisplayAdjustments_o_get_(packageInfo)
        DisplayAdjustmentsReImpl.mCompatInfo_o_get_(displayAdjustments)
    }

    private fun newLoadedApk(appInfo: ApplicationInfo, baseLoader: ClassLoader): Any {
        val includeCode = true
        return LoadedApkReImpl.newLoadedApk(
            activityThread,
            appInfo,
            compatInfo,
            baseLoader,
            false,
            includeCode && (applicationInfo.flags and ApplicationInfo.FLAG_HAS_CODE) != 0,
            false
        )
    }

    suspend fun loadApplication(apkPath: String): Any {
        return withContext(Dispatchers.IO) {
            val packageInfo = context.packageManager.getPackageArchiveInfo(apkPath, 0)!!
            val applicationInfo = packageInfo.applicationInfo
            Logger.i("applicationInfo:${applicationInfo.packageName}")
            val parentClassLoader = HostApkInfo::class.java.classLoader!!.parent!!
            Logger.i("开始创建LoadedApk:${parentClassLoader::class.java.canonicalName}")
            val loadedApk = newLoadedApk(
                applicationInfo, PathClassLoader(
                    apkPath,
                    apkPath,
                    parentClassLoader
                )
            )
            Logger.i("LoadedApk完成")
            mPackages[applicationInfo.packageName] = WeakReference(loadedApk)
            loadedApk
        }
    }

}