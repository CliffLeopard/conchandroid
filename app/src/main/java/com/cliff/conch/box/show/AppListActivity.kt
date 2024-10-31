package com.cliff.conch.box.show

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.conch.databinding.ActivityAppListBinding
import com.orhanobut.logger.Logger

class AppListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppListBinding
    private lateinit var viewModel: AppListViewModel
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            viewModel.loadApps(applicationContext)
        } else {
            Toast.makeText(this@AppListActivity, "应用列表授权失败", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AppListViewModel::class.java]
        val adapter = AppListAdapter(this)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                this@AppListActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.apps.observe(this) {
            adapter.submitList(it)
            binding.info.visibility =  if (it.isEmpty()) View.VISIBLE else View.GONE
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Logger.i("发起授权申请")
            permissionLauncher.launch(Manifest.permission.QUERY_ALL_PACKAGES)
        } else {
            viewModel.loadApps(applicationContext)
        }
    }
}