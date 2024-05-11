package com.cliff.conch.scene

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cliff.conch.R
import com.cliff.conch.databinding.ActivitySimpleCasesBinding
import com.cliff.conch.databinding.SectionItemBinding
import com.orhanobut.logger.Logger

class SimpleCasesActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimpleCasesBinding
    private lateinit var adapter: SAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleCasesBinding.inflate(layoutInflater)
        binding.root.setOnSystemUiVisibilityChangeListener { visibility ->
            when (visibility) {
                View.GONE ->
                    Logger.i("GONE")

                View.VISIBLE ->
                    Logger.i("VISIBLE")

                View.INVISIBLE ->
                    Logger.i("INVISIABLE")

                else ->
                    Logger.i("Nothing")

            }
        }
        setContentView(binding.root)
        prepareRecycleView()
    }

    private fun prepareRecycleView() {
        val diff = object : DiffUtil.ItemCallback<Case>() {
            override fun areItemsTheSame(oldItem: Case, newItem: Case): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Case, newItem: Case): Boolean {
                return oldItem.name == newItem.name
            }
        }
        adapter = SAdapter(diff)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        binding.list.addItemDecoration(
            DividerItemDecoration(
                this@SimpleCasesActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.submitList(Case.cases)
    }

    class SAdapter(diff: DiffUtil.ItemCallback<Case>) :
        ListAdapter<Case, SAdapter.SAViewHolder>(diff) {
        class SAViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val binding = SectionItemBinding.bind(view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SAViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.section_item, parent, false)
            return SAViewHolder(view)
        }

        override fun onBindViewHolder(holder: SAViewHolder, position: Int) {
            holder.binding.apply {
                val item = getItem(position)
                title.text = item.name
                this.root.setOnClickListener {
                    Logger.i("Click:${item.name}")
                    item.action(it)
                }
            }
        }
    }

    class Case(val name: String, val action: (View) -> Unit) {
        companion object {
            val cases: List<Case> = listOf(
                Case("R属性final问题") {

                },
                Case("透明Activity,View监控可见状态问题") {
                    Logger.i("ClickMe")
                }
            )
        }
    }
}


