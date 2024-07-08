package com.cliff.conch.scene

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cliff.conch.ConchApplication
import com.cliff.conch.databinding.ActivityFilePathBinding
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class FilePathActivity : AppCompatActivity() {
    lateinit var binding: ActivityFilePathBinding
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Logger.i("权限已授权")
                getApplicationIfo()

            } else {
                Logger.i("没有授权")
                denied()
            }
        }

    private fun denied() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilePathBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 系统目录
        addCase("系统应用安装目录", "/system/app/appName")

        // pm package list | grep -i packageName
        // pm path packageName

        // 存储有base.apk,lib; release应用会存在oat目录，debug文件则没有。oat目录中存储着arm64/base.odex  arm64/base.vdex
        // 自己的apk目录也可以直接读取，不需要读取其它权限
        addCase("用户apk安装的文件夹", "/data/app/~~随机数==/packageName-随机数==")
        val file = File(packageManager.getApplicationInfo(packageName,0).sourceDir)
        addCase("用户apk安装的文件夹", file.absolutePath)
        addCase(
            "用户apk安装的文件夹内文件",
            file.listFiles()?.joinToString { it.absolutePath + ";" } ?: "null")

        // 安装 add("application/vnd.android.package-archive", "apk");

//        /system/framwork：保存的是资源型的应用程序，它们用来打包资源文件。
//        /system/app ：系统自带的应用程序，获得adb root权限才能删除
//        /data/app-private：保存受DRM保护的私有应用程序。
//        /vendor/app：保存设备厂商提供的应用程序。
//        /data/app ：用户程序安装的文件夹。安装时把apk文件复制到此文件夹
//        /data/data ：存放应用程序的数据
//        /data/dalvik-cache：将apk中的dex文件安装到dalvik-cache文件夹下(dex文件是dalvik虚拟机的可运行文件,当然，ART–Android Runtime的可运行文件格式为oat。启用ART时，系统会运行dex文件转换至oat文件)
//        /data/system ：该文件夹下的packages.xml文件。相似于Windows的注冊表，这个文件是在解析apk时由writeLP()创建的。里面记录了系统的permissions，以及每一个apk的name,codePath,flags,ts,version,uesrid等信息。这些信息主要通apk的AndroidManifest.xml解析获取，解析完apk后将更新信息写入这个文件并保存到flash，下次开机直接从里面读取相关信息加入到内存相关列表中。当有apk升级，安装或删除时会更新这个文件。


        // 内部存储，私有数据目录 -- 无需权限申请
        // /data/user/0/com.cliff.conch 等价于 /data/data/com.cliff.conch
        addCase("=========================", "内部存储-私有数据-无需权限申请-外部不可访问")
        addCase(
            "context.getDir(\"Hello\",MODE_APPEND):",
            getDir("Hello", MODE_APPEND).absolutePath
        ) // 自动创建app_Hello目录
        addCase("context.getCacheDir():", cacheDir.absolutePath)
        addCase("context.getFilesDir():", filesDir.absolutePath)
        addCase("context.getDataDir():", dataDir.absolutePath)
        addCase("context.getCodeCacheDir():", codeCacheDir.absolutePath)
        addCase("context.getDir(null):", getDir(null, MODE_PRIVATE).absolutePath)
        addCase("context.fileList():", fileList().contentToString())


        // 外部存储私有数据目录 -- 无需权限申请
        //  /storage/emulated/0/Android/data/com.cliff.conch
        //  /sdcard/Android/data/com.cliff.conch
        addCase(
            "=========================",
            "外部存储-私有数据-无需权限申请-外部通过FileProvider访问"
        )
        addCase("context.getExternalCacheDir():", externalCacheDir?.absolutePath ?: "null")
        addCase("context.getExternalCacheDirs():", externalCacheDirs.contentToString() ?: "null")
        addCase("context.getExternalMediaDirs():", externalMediaDirs.contentToString())
        addCase(
            "context.getExternalFilesDir():",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath ?: "null"
        )
        addCase(
            "context.getExternalFilesDir(hello):",
            getExternalFilesDir("Hello")?.absolutePath ?: "null"
        )
        addCase(
            "context.getExternalFilesDir(null):",
            getExternalFilesDir(null)?.absolutePath ?: "null"
        )

        // 外部存储，公有有数据目录 -- 需要权限申请 EXTERNAL_STORAGE
        addCase("=========================", "外部存储-公有数据-需要权限申请")
        addCase(
            "Environment.getDownloadCacheDirectory().getAbsolutePath():",
            Environment.getDownloadCacheDirectory().absolutePath
        )
        addCase(
            "Environment.getDataDirectory().getAbsolutePath():",
            Environment.getDataDirectory().absolutePath
        )
        addCase(
            "Environment.getExternalStorageDirectory().getAbsolutePath():",
            Environment.getExternalStorageDirectory().absolutePath
        )
        addCase(
            "Environment.getExternalStoragePublicDirectory().getAbsolutePath():",
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).absolutePath
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Logger.i("发起授权申请")
            requestPermissionLauncher.launch(Manifest.permission.QUERY_ALL_PACKAGES)
        } else {
            getApplicationIfo()
        }

        lifecycleScope.launch {
            getPkgInfo()
        }


        // APK安装
        // File apkFile;
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        //context.startActivity(intent);
    }

    private fun getApplicationIfo() {
        val content = ConchApplication.context
        val appInfo = content.applicationInfo
        val appInfos = packageManager.getInstalledApplications(0)
        val blackInfo = packageManager.getApplicationInfo("top.niunaijun.blackboxa64_beta", 0)
        Logger.i("appInfos:${appInfos.size}")
        Logger.i("blackInfo:${blackInfo.sourceDir}")
        Logger.i("blackInfo:${blackInfo.dataDir}")
        Logger.i("blackInfo:${blackInfo.isVirtualPreload}")

        addCase("appinfo.sourceDir", blackInfo.sourceDir)
        addCase("appinfo.publicSourceDir", blackInfo.publicSourceDir)
        addCase("appinfo.splitNames", blackInfo.splitNames?.contentToString() ?: "null")
        addCase("appinfo.splitSourceDirs", blackInfo.splitSourceDirs?.contentToString() ?: "null")
        addCase(
            "appinfo.splitPublicSourceDirs",
            blackInfo.splitPublicSourceDirs?.contentToString() ?: "null"
        )
        addCase(
            "appinfo.sharedLibraryFiles",
            blackInfo.sharedLibraryFiles?.contentToString() ?: "null"
        )
        addCase("appinfo.dataDir", blackInfo.dataDir)
        addCase("appinfo.deviceProtectedDataDir", blackInfo.deviceProtectedDataDir)
        addCase("appinfo.nativeLibraryDir", blackInfo.nativeLibraryDir)
        addCase("appinfo.uid", blackInfo.uid.toString())
        addCase("appinfo.appComponentFactory", blackInfo.appComponentFactory)
    }

    private suspend fun getPkgInfo() {
        withContext(Dispatchers.IO) {
            val context = ConchApplication.context
            // ShellActivity里执行了将 /data/app/随机数/packageName-随机数/base.apk 文件存储到了 /sdcard/Android/data/com.cliff.conch/files/apk/
            val apkFile = File(context.getExternalFilesDir("apk"), "base.apk")
            if (!apkFile.exists()) {
                val baseApkDir = context.applicationInfo.sourceDir
                FileInputStream(baseApkDir).use { inputStream ->
                    apkFile.outputStream().buffered().use { bufferOutputStream ->
                        inputStream.copyTo(bufferOutputStream)
                    }
                }
            }
            val pkgInfo = context.packageManager.getPackageArchiveInfo(
                apkFile.absolutePath,
                PackageManager.GET_ACTIVITIES
            )

            if (pkgInfo != null) {
                Logger.i(pkgInfo.activities.joinToString { it.name })
            } else {
                Logger.i("pkgInfo is null")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun addCase(title: String, content: String?) {
        val textView = TextView(this)
        textView.text = "$title\n${content ?: ""}"
        textView.setSingleLine(false)
        textView.maxLines = 10

        val parameter = LinearLayout.LayoutParams(MATCH_PARENT, 200)
        textView.setTextColor(resources.getColor(android.R.color.black))
        textView.gravity = Gravity.START
        binding.container.addView(textView, parameter)

        val baseLine = View(this)
        baseLine.setBackgroundColor(Color.BLACK)
        val parameter2 = LinearLayout.LayoutParams(MATCH_PARENT, 2)
        binding.container.addView(baseLine, parameter2)


        val container = binding.container.layoutParams
        container.height += 202
        binding.container.layoutParams = container
    }
}