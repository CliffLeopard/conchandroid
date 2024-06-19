package com.cliff.conch.box.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import com.cliff.conch.ConchApplication

@SuppressLint("StaticFieldLeak")
object FileUtil {
    val context = ConchApplication.context

    // 内部存储 /data/data/packageName/下
    fun getInternalRootDir(): String {
        return context.dataDir.absolutePath
    }

    fun getInternalCacheDir(): String {
        return context.cacheDir.absolutePath
    }

    fun getInternalFilesDir(): String {
        return context.filesDir.absolutePath
    }

    fun getInternalCodeCacheDir(): String {
        return context.codeCacheDir.absolutePath
    }

    // app_fileName的形式出现
    fun getInternalFileDir(fileName: String): String {
        return context.getDir(fileName, Context.MODE_APPEND).absolutePath
    }

    // 外部私有存储，通过FileProvider可向外部提供文件访问权限和uri
    fun getExternalCacheDir(): String? {
        return context.externalCacheDir?.absolutePath
    }

    //    Environment.DIRECTORY_MUSIC,
    //    Environment.DIRECTORY_PODCASTS,
    //    Environment.DIRECTORY_RINGTONES,
    //    Environment.DIRECTORY_ALARMS,
    //    Environment.DIRECTORY_NOTIFICATIONS,
    //    Environment.DIRECTORY_PICTURES,
    //    Environment.DIRECTORY_MOVIES
    // 或者自定义，比如Hello, 创建 /sdcard/Android/data/packageName/files/Hello
    fun getExternalFilesDir(type: String): String? {
        return context.getExternalFilesDir(type)?.absolutePath
    }

    //    /sdcard/Android/data/packageName/files 目录
    fun getExternalFilesDir(): String? {
        return context.getExternalFilesDir(null)?.absolutePath
    }


    // 公共存储需要申请权限 /sdcard/
    fun getPublicRootDir(): String {
        return Environment.getExternalStorageDirectory().absolutePath
    }

    //    /data目录
    fun getPublicDataDir(): String {
        return Environment.getDataDirectory().absolutePath
    }
    // /sdcard/Download目录
    fun getPublicDownloadDir(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
    }
    //  /data/cache目录
    fun getPublicDownloadCacheDir(): String {
        return Environment.getDownloadCacheDirectory().absolutePath
    }

}