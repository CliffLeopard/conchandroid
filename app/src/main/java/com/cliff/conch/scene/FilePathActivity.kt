package com.cliff.conch.scene

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityFilePathBinding

class FilePathActivity : AppCompatActivity() {
    lateinit var binding: ActivityFilePathBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilePathBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 内部存储，私有数据目录 -- 无需权限申请
        addCase("=========================", "内部存储-私有数据-无需权限申请-外部不可访问")
        addCase(
            "context.getDir(\"Hello\",MODE_APPEND):",
            getDir("Hello", MODE_APPEND).absolutePath
        ) // 自动创建app_Hello目录
        addCase("context.getCacheDir():", cacheDir.absolutePath)
        addCase("context.getFilesDir():", filesDir.absolutePath)
        addCase("context.getDataDir():", dataDir.absolutePath)
        addCase("context.fileList():", fileList().contentToString())


        // 外部存储私有数据目录 -- 无需权限申请
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
    }

    @SuppressLint("SetTextI18n")
    fun addCase(title: String, content: String) {
        val textView = TextView(this)
        textView.text = "$title\n$content"
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