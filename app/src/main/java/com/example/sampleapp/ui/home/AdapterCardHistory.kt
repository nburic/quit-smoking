package com.example.sampleapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.data.models.home.ProgressHistoryItem
import com.example.sampleapp.databinding.MpItemHistoryBinding


class AdapterCardHistory(private var items: List<ProgressHistoryItem> = emptyList()) : RecyclerView.Adapter<AdapterCardHistory.ViewHolder>() {

    fun setItems(items: List<ProgressHistoryItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MpItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindData(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: MpItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: ProgressHistoryItem) {
            binding.tvItemTitle.text = item.title
            binding.tvItemValue.text = item.value
            binding.ivItemIcon.setImageResource(item.icon)
        }
    }
}