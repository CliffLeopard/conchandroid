package com.cliff.conch.scene

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
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
        addCase("fileList():", fileList().contentToString())
        addCase("filesDir:", filesDir.absolutePath)
    }

    @SuppressLint("SetTextI18n")
    fun addCase(title: String, content: String) {
        val button = TextView(this)
        button.text = "$title : $content"
        val parameter = LinearLayout.LayoutParams(MATCH_PARENT, 80)
        button.setTextColor(resources.getColor(android.R.color.black))
        button.gravity = Gravity.CENTER
        binding.container.addView(button, parameter)
        val container = binding.container.layoutParams
        container.height += 80
        binding.container.layoutParams = container
    }
}