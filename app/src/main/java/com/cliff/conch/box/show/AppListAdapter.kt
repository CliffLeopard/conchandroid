package com.cliff.conch.box.show

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cliff.conch.R
import com.cliff.conch.databinding.AppListItemBinding
import reflect.android.app.Activity

/**
 * @Author CliffLeopard
 * @Email  precipiceleopard@gmail.com
 * @Time   2024/10/31 07:50
 */
class AppListAdapter(private val context: AppCompatActivity) :
    ListAdapter<ApplicationInfo, AppListAdapter.AppListViewHolder>(sectionDiff) {
    class AppListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = AppListItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.app_list_item, parent, false)
        return AppListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppListViewHolder, position: Int) {
        holder.binding.apply {
            val info = getItem(position)
            val packageManager = context.packageManager
            this.label.text = info.loadLabel(packageManager).toString()
            this.packageName.text = info.packageName
            this.icon.setImageDrawable(info.loadIcon(packageManager))
            this.root.setOnClickListener {
                context.setResult(RESULT_OK, Intent().apply {
                    putExtra("info",info)
                })
                context.finish()
            }
        }
    }
}

val sectionDiff: DiffUtil.ItemCallback<ApplicationInfo> = object : DiffUtil.ItemCallback<ApplicationInfo>() {
    override fun areItemsTheSame(oldItem: ApplicationInfo, newItem: ApplicationInfo) =
        oldItem.packageName == newItem.packageName

    override fun areContentsTheSame(oldItem: ApplicationInfo, newItem: ApplicationInfo) =
        oldItem.packageName == newItem.packageName
}