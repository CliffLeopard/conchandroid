package com.cliff.conch.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityPackageAppInfoBinding

class PackageAppInfo : BaseActivity<ActivityPackageAppInfoBinding>() {
    private val viewModel: PackageAppInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            viewModel.getLoadedApkInfo()
        }
    }

    override fun initBinding() {
        binding = ActivityPackageAppInfoBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }
}