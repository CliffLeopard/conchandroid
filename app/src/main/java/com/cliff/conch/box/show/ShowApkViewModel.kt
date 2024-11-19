package com.cliff.conch.box.show

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.cliff.conch.box.util.FileUtil
import com.orhanobut.logger.Logger
import reflect.android.content.pm.parsing.ApkLiteParseUtils
import reflect.android.content.pm.parsing.ApkLiteParseUtils__Functions.parsePackageLite
import reflect.android.content.pm.parsing.PackageLite
import reflect.android.content.pm.parsing.PackageLite__Functions.__instance__
import reflect.android.content.pm.parsing.result.ParseResult
import reflect.android.content.pm.parsing.result.ParseResult__Functions.__instance__
import reflect.android.content.pm.parsing.result.ParseTypeImpl
import reflect.android.content.pm.parsing.result.ParseTypeImpl__Functions.__instance__
import reflect.android.content.pm.parsing.result.ParseTypeImpl__Functions.forDefaultParsing
import java.io.FileInputStream

/**
 * @Author CliffLeopard
 * @Email  precipiceleopard@gmail.com
 * @Time   2024/10/29 11:40
 */
class ShowApkViewModel : ViewModel() {
    fun processApk(context: Context, uri: Uri) {
        Logger.i(uri.path ?: "NULL")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            virtualInstallApk(context, uri)
        }
    }

    fun processApp(context: Context, apkInfo: ApplicationInfo) {
        Logger.i(apkInfo.loadLabel(context.packageManager).toString())
        val apkPath = apkInfo.sourceDir
        Logger.i(apkPath)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun virtualInstallApk(context: Context, uri: Uri) {
        val file = FileUtil.childrenAppExternalCache()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        val newUri = FileProvider.getUriForFile(context, "com.cliff.conch.fileprovider", file)
        Logger.i("virtualInstallApk:${file.length()}")
        val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
        params.setPackageSource(PackageInstaller.PACKAGE_SOURCE_LOCAL_FILE)
        params.setReferrerUri(newUri)
        params.setInstallReason(PackageManager.INSTALL_REASON_USER)

        val input = ParseTypeImpl.__instance__(ParseTypeImpl.forDefaultParsing())
        val result = ParseResult.__instance__(ApkLiteParseUtils.parsePackageLite(input.reset(), file, 0))
        if (result.isError()) {
            params.setSize(file.length())
        } else {
            val pkg = PackageLite.__instance__(result.getResult())
            params.setAppPackageName(pkg.getPackageName())
            params.setInstallLocation(pkg.getInstallLocation())
            params.setSize(file.length())
        }

        val sessionId = context.packageManager.packageInstaller.createSession(params)
        val installer = context.packageManager.packageInstaller
        val sessionInfo = installer.getSessionInfo(sessionId)
        if (sessionInfo != null && !sessionInfo.isActive) {
            val session = installer.openSession(sessionId)
            session.setStagingProgress(0f)
            FileInputStream(file).use { inputStream ->
                val sizeBytes = file.length()
                session
                    .openWrite("PackageInstaller", 0, sizeBytes).use { out ->
                        val buffer = ByteArray(1024 * 1024)
                        var sum:Double = 0.0
                        while (true) {
                            val numRead = inputStream.read(buffer)
                            if (numRead == -1) {
                                session.fsync(out)
                                break
                            }
                            out.write(buffer, 0, numRead)
                            if (sizeBytes > 0) {
                                sum += numRead.toDouble()
                                val fraction = (sum / sizeBytes.toDouble()) * 100
                                Logger.i("Progress: $fraction%")
                            }
                        }
                    }
            }
            Logger.i("Session Commit")
        }
    }
}