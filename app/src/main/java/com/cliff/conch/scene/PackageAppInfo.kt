package com.cliff.conch.scene

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityPackageAppInfoBinding
import com.cliff.hidden.HiddenApi
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
            val clzz = Class.forName("android.view.DisplayAdjustments")
            Logger.i("fields:${HiddenApi.getInstanceFields(clzz).joinToString { it.name }}")
            val field = HiddenApi.getInstanceFiled("mCompatInfo",clzz)
            Logger.i("field:${field?.name}")
        }
    }
}