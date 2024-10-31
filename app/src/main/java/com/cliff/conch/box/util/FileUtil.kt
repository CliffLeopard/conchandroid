package com.cliff.conch.box.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import com.cliff.conch.ConchApplication
import java.io.File

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


    /**
     * /data/data/宿主包名/app_children
     */
    fun childrenRootDir(): File {
        return context.getDir(CHILDREN_ROOT, Context.MODE_APPEND)
    }

    /**
     * /data/data/宿主包名/app_children/cache
     */
    fun childrenCacheDir(): File {
        val file = File(childrenRootDir(), CHILDREN_CACHE)
        if (!file.exists()) file.mkdirs()
        return file
    }

    /**
     * /data/data/宿主包名/app_children/cache/time.apk
     */
    fun childCache(): File {
        return File.createTempFile("temp-", ".apk", childrenCacheDir())
    }

    /**
     * /data/data/宿主包名/app_children/child包名/
     */
    fun childRootDir(packageName: String): File {
        val file = File(childrenRootDir(), packageName)
        if (!file.exists()) file.mkdirs()
        return file
    }

    /**
     * /data/data/宿主包名/app_children/child包名/sourceDir
     */
    fun childSourceDir(packageName: String): File {
        val file = File(childRootDir(packageName), SOURCE_DIR)
        if (!file.exists()) file.mkdirs()
        return file
    }

    /**
     * /data/data/宿主包名/app_children/child包名/internalDir
     */
    fun childAppInternalDir(packageName: String): File {
        val file = File(childRootDir(packageName), INTERNAL_DIR)
        if (!file.exists()) file.mkdirs()
        return file
    }

    /**
     * /sdcard/Android/data/宿主包名/files/Children/child包名
     */
    fun childAppExternalDir(packageName: String): File {
        val external = context.getExternalFilesDir(CHILDREN_ROOT)
        val file = File(external, packageName)
        if (!file.exists()) file.mkdirs()
        return file
    }

    /**
     * /sdcard/Android/data/宿主包名/files/Children/cache/temp-[random].apk
     */
    fun childrenAppExternalCache(): File {
        val externalCacheDir = File(context.getExternalFilesDir(CHILDREN_ROOT), CHILDREN_CACHE)
        if (!externalCacheDir.exists()) externalCacheDir.mkdirs()
        return File.createTempFile("temp-", ".apk", externalCacheDir)
    }

    /**
     * /data/data/宿主包名/children/child包名/sourceDir/base.dex
     */
    fun childAppDex(packageName: String): File {
        return File(childSourceDir(packageName), DEX_FILE)
    }

    fun childAppBaseFile(packageName: String):File {
        return File(childSourceDir(packageName), BASE_FILE)
    }

    /**
     * /data/data/宿主包名/children/child包名/sourceDir/lib
     */
    fun childAppNativeLibrary(packageName: String): File {
        val file = File(childSourceDir(packageName), NATIVE_LIBRARY)
        if (!file.exists()) file.mkdirs()
        return file
    }

    private const val CHILDREN_ROOT = "children"
    private const val CHILDREN_CACHE = "cache"
    private const val SOURCE_DIR = "sourceDir"
    private const val INTERNAL_DIR = "internalDir"
    private const val DEX_FILE = "base.dex"
    private const val BASE_FILE = "base.apk"
    private const val NATIVE_LIBRARY = "lib"
}

/**
 * 一个应用的目录分为以下几种：
 * 1. sourceDir base.apk存在目录
 *      普通应用: /data/app/随机数/packageName-随机数/base.apk
 *      child应用: /data/data/宿主包名/children/packageName/sourceDir/base.apk
 *  2. internalDir:
 *      普通应用: /data/data/包名/
 *      child应用: /data/data/宿主包名/children/packageName/internalDir/..
 *  3. externalDir:
 *      普通应用:  /sdcard/Android/data/应用包名
 *      child应用:/sdcard/Android/data/宿主包名/children/packageName/externalDir/
 *  4. publicDir:
 *  公共目录，普通应用和child应用相同，如果要使用需要申请权限；
 */