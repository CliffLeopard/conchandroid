package com.cliff.conch.install

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityInstallPackageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstallPackageActivity : BaseActivity<ActivityInstallPackageBinding>() {
    private val viewModel: InstallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.fab.bringToFront()
        binding.list.layoutManager = GridLayoutManager(this, 5)
        binding.list.adapter = AppItemAdapter(viewModel.apps)
        binding.fab.setOnClickListener {
            viewModel.install()
        }
    }

    override fun initBinding() {
        binding = ActivityInstallPackageBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }
}