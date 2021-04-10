package com.example.sampleapp.ui.store

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.data.db.store.StoreItemEntity
import com.example.sampleapp.databinding.ItemStoreBinding
import com.example.sampleapp.ui.settings.SettingsFragment

class AdapterStoreItems(private val onDeleteClick: (item: StoreItemEntity) -> Unit,
                        private val onPurchaseClick: (item: StoreItemEntity) -> Unit) : RecyclerView.Adapter<AdapterStoreItems.ViewHolder>() {

    private var items: List<StoreItemEntity> = emptyList()
    private var money: Int = 0

    fun setItems(items: List<StoreItemEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setMoney(money: Int) {
        this.money = money
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

        holder.binding.tvPurchase.setOnClickListener {
            onPurchaseClick.invoke(item)
        }

        holder.bind(item, money)
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: StoreItemEntity, money: Int) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = item.price.toString() + SettingsFragment.CURRENCY

            when (item.bought) {
                true -> binding.pbStatus.progress = 100
                false -> {
                    val percent = money * 100 / item.price
                    when {
                        percent >= 100 -> binding.pbStatus.progress = 100
                        else -> binding.pbStatus.progress = percent
                    }
                }
            }
        }
    }
}