package com.cliff.conch.install

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cliff.conch.databinding.ActivityInstallPackageBinding

class InstallPackageActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstallPackageBinding
    private val viewModel: InstallViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstallPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.bringToFront()
        binding.list.layoutManager = GridLayoutManager(this, 5)
        binding.list.adapter = AppItemAdapter(viewModel.apps)
        binding.fab.setOnClickListener {
            viewModel.install()
        }
    }
}