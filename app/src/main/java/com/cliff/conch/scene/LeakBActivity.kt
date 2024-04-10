package com.cliff.conch.scene

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityLeakBactivityBinding

class LeakBActivity : AppCompatActivity() {
    lateinit var binding: ActivityLeakBactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeakBactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }
    }
}