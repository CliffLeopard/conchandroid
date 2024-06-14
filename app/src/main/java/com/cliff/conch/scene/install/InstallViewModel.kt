package com.cliff.conch.scene.install

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.conch.ConchApplication
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
            installApk()
        }
    }

    private suspend fun installApk() {
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
            Logger.i("apkUri:${apkUri}")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            context.startActivity(intent)
        }
    }
}