package com.cliff.conch.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.conch.databinding.ActivityReflectBinding
import com.cliff.conch.scene.reflect.ReflectViewModel
import com.cliff.conch.ui.home.SectionAdapter

class ReflectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReflectBinding
    private lateinit var viewModel: ReflectViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReflectBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ReflectViewModel::class.java]
        setContentView(binding.root)
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
}