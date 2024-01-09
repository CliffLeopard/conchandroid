package com.cliff.conch.scene

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityAnractivityBinding

class ANRActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            println("begin run")
            Thread.sleep(300000)
            println("end run")
        }, 1500)
    }
}