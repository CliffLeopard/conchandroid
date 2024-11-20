package com.cliff.conch.scene

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityCoroutineBinding
import com.cliff.conch.scene.coroutine.CoroutineViewModel
import com.cliff.conch.ui.home.SectionAdapter
import com.orhanobut.logger.Logger

class CoroutineActivity : BaseActivity<ActivityCoroutineBinding>() {
    private lateinit var viewModel: CoroutineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = SectionAdapter(this)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        binding.list.addItemDecoration(
            DividerItemDecoration(
                this@CoroutineActivity, DividerItemDecoration.VERTICAL
            )
        )
        viewModel.sections.observe(this) {
            Logger.d("observe data:" + it.size)
            adapter.submitList(it)
        }
    }

    override fun initBinding() {
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CoroutineViewModel::class.java]
    }

    override fun mainView(): View {
        return binding.main
    }
}