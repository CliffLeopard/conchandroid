package com.cliff.conch.box.show

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cliff.conch.databinding.ActivityShowApkInfoBinding
import com.orhanobut.logger.Logger

class ShowApkInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowApkInfoBinding
    private lateinit var viewModel: ShowApkViewModel
    private val apkLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.processApk(this, it)
        }
    }

    private val appLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { actvityResult ->
        if (actvityResult.resultCode == RESULT_OK && actvityResult.data != null) {
            actvityResult.data?.getParcelableExtra<ApplicationInfo>("info")?.let {
                viewModel.processApp(this, it)
            }
        } else {
            Toast.makeText(this, "应用选择失败", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowApkInfoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ShowApkViewModel::class.java]
        setContentView(binding.root)
        binding.selectApk.setOnClickListener {
            apkLauncher.launch("*/*")
        }

        binding.selectApp.setOnClickListener {
            appLauncher.launch(Intent(this, AppListActivity::class.java))
        }
    }
}