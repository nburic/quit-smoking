package com.example.sampleapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.data.models.home.ProgressStatsItem
import com.example.sampleapp.databinding.MpItemStatsBinding


class AdapterCardStats(private var items: List<ProgressStatsItem> = emptyList()) : RecyclerView.Adapter<AdapterCardStats.ViewHolder>() {

    fun setItems(items: List<ProgressStatsItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MpItemStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindData(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: MpItemStatsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: ProgressStatsItem) {
            binding.tvItemTitle.text = item.title
            binding.tvItemValue.text = item.value
            binding.ivItemIcon.setImageResource(item.icon)
        }
    }
}