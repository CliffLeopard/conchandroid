package com.cliff.conch.scene.install

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cliff.conch.R
import com.cliff.conch.databinding.AppItemBinding
import com.orhanobut.logger.Logger

class AppItemAdapter(
    private val data: LiveData<MutableList<AppItem>>
) : RecyclerView.Adapter<AppItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AppItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data.value?.get(position)?.let {
            Logger.i("onBindViewHolder: ${it.name}")
            holder.binding.title.text = it.name
            Glide.with(holder.binding.icon)
                .load(R.drawable.pdf)
                .transform(RoundedCorners(8)) // 数字根据自己需求来改
                .into(holder.binding.icon)
        }
    }

    inner class ViewHolder(val binding: AppItemBinding) : RecyclerView.ViewHolder(binding.root)
}