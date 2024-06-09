package com.cliff.conch.scene

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.cliff.conch.ConchApplication
import com.cliff.conch.databinding.ActivityShellBinding
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class ShellActivity : AppCompatActivity() {
    lateinit var binding: ActivityShellBinding
    private val viewModel: ShellViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.runShell.setOnClickListener {
            lifecycleScope.run {
                viewModel.showApkLocation()
            }
        }
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
                val len = 1024 * 10
                val buffer = CharArray(len + 1)
                var number = 0
                BufferedReader(InputStreamReader(FileInputStream(apkFile))).use { bufferReader ->
                    OutputStreamWriter(FileOutputStream(newApkFile)).use { streamWriter ->
                        while (true) {
                            number = bufferReader.read(buffer, 0, len)
                            if (number == -1) {
                                break
                            } else {
                                streamWriter.write(buffer, 0, number)
                            }
                        }
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