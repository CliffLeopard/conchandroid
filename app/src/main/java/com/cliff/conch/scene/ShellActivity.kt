package com.cliff.conch.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.cliff.common.BaseActivity
import com.cliff.conch.ConchApplication
import com.cliff.conch.databinding.ActivityShellBinding
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class ShellActivity : BaseActivity<ActivityShellBinding>() {
    private val viewModel: ShellViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.runShell.setOnClickListener {
            lifecycleScope.run {
                viewModel.showApkLocation()
            }
        }
    }

    override fun initBinding() {
        binding = ActivityShellBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }

    class ShellViewModel : ViewModel() {

        fun showApkLocation() {
            viewModelScope.launch {
                runShell()
            }
        }

        private suspend fun runShell() {
            val context = ConchApplication.context
            val pkgName = context.applicationInfo.packageName
            withContext(Dispatchers.IO) {
                val process = Runtime.getRuntime().exec("pm path $pkgName")
                val result = StringBuilder()
                var line: String?
                process.waitFor()
                BufferedReader(InputStreamReader(process.inputStream)).use { bufferReader ->
                    do {
                        line = bufferReader.readLine()
                        line?.let {
                            result.append(it)
                        }
                    } while (line != null)
                }

                val apkPath = result.toString().removePrefix("package:").trim()
                val apkFile = File(apkPath)
                val newApkFile = File(context.getExternalFilesDir("apk"), apkFile.name)

                FileInputStream(apkFile).use { inputStream ->
                    newApkFile.outputStream().buffered().use { bufferOutputStream ->
                        inputStream.copyTo(bufferOutputStream)
                    }
                }

                withContext(Dispatchers.Main) {
                    Logger.i("ShellCommandResult:${apkFile.absolutePath}")
                    Logger.i("ShellCommandResult:${newApkFile.absolutePath}")
                }
            }
        }
    }
}