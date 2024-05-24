package com.cliff.conch.box

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.conch.databinding.ActivityEgoBinding
import com.cliff.conch.ui.home.SectionAdapter
import com.orhanobut.logger.Logger

class EgoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEgoBinding
    private lateinit var viewModel: EgoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEgoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EgoViewModel::class.java]
        setContentView(binding.root)

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
}