package com.cliff.conch.scene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cliff.conch.R
import com.cliff.conch.databinding.ActivityEventBusBinding
import com.cliff.conch.databinding.ActivityRetrofitBinding

class RetrofitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRetrofitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}