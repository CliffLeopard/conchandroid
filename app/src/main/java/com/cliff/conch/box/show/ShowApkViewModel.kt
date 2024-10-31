package com.cliff.conch.box.show

import android.content.Context
import android.content.pm.ApplicationInfo
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger

/**
 * @Author CliffLeopard
 * @Email  precipiceleopard@gmail.com
 * @Time   2024/10/29 11:40
 */
class ShowApkViewModel : ViewModel() {
    fun processApk(context: Context, uri: Uri) {
        Logger.i(uri.path ?: "NULL")
    }

    fun processApp(context: Context, apkInfo: ApplicationInfo) {
        Logger.i(apkInfo.loadLabel(context.packageManager).toString())
        val apkPath = apkInfo.sourceDir
        Logger.i(apkPath)
    }
}