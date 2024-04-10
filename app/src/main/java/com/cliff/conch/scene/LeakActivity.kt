package com.cliff.conch.scene

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityLeakBinding
import com.cliff.conch.startActivity


class LeakActivity : AppCompatActivity() {
    lateinit var binding: ActivityLeakBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeakBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.setOnClickListener { _ ->
            startActivity(LeakBActivity::class.java)
        }

        Handler(Looper.getMainLooper()).post{};
    }
}