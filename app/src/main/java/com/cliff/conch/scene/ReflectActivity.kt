package com.cliff.conch.scene

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityReflectBinding
import com.cliff.conch.scene.reflect.ReflectViewModel
import com.cliff.conch.ui.home.SectionAdapter

class ReflectActivity : BaseActivity<ActivityReflectBinding>() {
    private lateinit var viewModel: ReflectViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = SectionAdapter(this)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        binding.list.addItemDecoration(
            DividerItemDecoration(
                this@ReflectActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.sections.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun initBinding() {
        binding = ActivityReflectBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ReflectViewModel::class.java]
    }
    override fun mainView(): View {
        return binding.main
    }
}