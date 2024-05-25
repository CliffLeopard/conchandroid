package com.cliff.conch.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.conch.databinding.ActivityCoroutineBinding
import com.cliff.conch.scene.coroutine.CoroutineViewModel
import com.cliff.conch.ui.home.SectionAdapter
import com.orhanobut.logger.Logger

class CoroutineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutineBinding
    private lateinit var viewModel: CoroutineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CoroutineViewModel::class.java]
        setContentView(binding.root)

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
}