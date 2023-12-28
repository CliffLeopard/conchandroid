package com.cliff.conch.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivitySelfDefineViewBinding

class SelfDefineViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelfDefineViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelfDefineViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}