package com.cliff.conch.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityRetrofitBinding

class RetrofitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRetrofitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}