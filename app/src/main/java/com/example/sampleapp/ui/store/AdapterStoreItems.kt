package com.example.sampleapp.ui.store

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.data.db.store.StoreItemEntity
import com.example.sampleapp.databinding.ItemStoreBinding
import com.example.sampleapp.ui.settings.SettingsFragment

class AdapterStoreItems(private val onDeleteClick: (item: StoreItemEntity) -> Unit) : RecyclerView.Adapter<AdapterStoreItems.ViewHolder>() {

    private var items: List<StoreItemEntity> = emptyList()

    fun setItems(items: List<StoreItemEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.imgDelete.setOnClickListener {
            onDeleteClick.invoke(item)
        }

        holder.bind(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: StoreItemEntity) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = item.price.toString() + SettingsFragment.CURRENCY
            binding.pbStatus.progress = 40
        }
    }
}