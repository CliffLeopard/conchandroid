package com.cliff.conch.scene

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cliff.common.HiddenCase
import com.cliff.conch.databinding.ActivityPackageAppInfoBinding
import com.orhanobut.logger.Logger

class PackageAppInfo : AppCompatActivity() {
    lateinit var binding: ActivityPackageAppInfoBinding
    private val viewModel: PackageAppInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageAppInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            viewModel.getLoadedApkInfo()
        }
        binding.hidden.setOnClickListener {
            val field = HiddenCase.hiddenInfo()
            Logger.i("fields:${field}")
        }
    }
}