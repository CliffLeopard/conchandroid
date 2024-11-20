package com.cliff.conch.box

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityEgoBinding
import com.cliff.conch.ui.home.SectionAdapter
import com.orhanobut.logger.Logger

class EgoActivity : BaseActivity<ActivityEgoBinding>() {
    private lateinit var viewModel: EgoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = SectionAdapter(this)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        binding.list.addItemDecoration(
            DividerItemDecoration(
                this@EgoActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.sections.observe(this) {
            Logger.d("observe data:" + it.size)
            adapter.submitList(it)
        }
    }

    override fun initBinding() {
        binding = ActivityEgoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EgoViewModel::class.java]
    }

    override fun mainView(): View {
        return binding.main
    }
}