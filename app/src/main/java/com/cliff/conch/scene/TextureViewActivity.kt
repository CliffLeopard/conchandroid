package com.cliff.conch.scene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cliff.conch.R
import com.cliff.conch.databinding.ActivityTextureViewBinding

class TextureViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityTextureViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextureViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val surfaceView = binding.hdrSurface
    }
}