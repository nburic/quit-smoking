package com.example.sampleapp.ui.store

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.data.db.store.StoreItemEntity
import com.example.sampleapp.databinding.ItemStoreBinding
import com.example.sampleapp.ui.settings.SettingsFragment
import com.example.sampleapp.util.hide
import com.example.sampleapp.util.show

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
            val context = binding.root.context
            binding.tvTitle.text = item.title
            binding.tvPrice.text = item.price.toString() + SettingsFragment.CURRENCY

            when (item.bought) {
                true -> {
                    binding.wrapper.setBackgroundColor(context.getColor(R.color.lightGreen))
                    binding.pbStatus.progress = 100
                    binding.tvPurchase.text = context.getString(R.string.store_bought)
                    binding.pbStatus.hide()
                }
                false -> {
                    binding.wrapper.setBackgroundColor(0)
                    val percent = money * 100 / item.price
                    when {
                        percent >= 100 -> binding.pbStatus.progress = 100
                        else -> binding.pbStatus.progress = percent
                    }
                    binding.tvPurchase.text = context.getString(R.string.store_purchase)
                    binding.pbStatus.show()
                }
            }
        }
    }
}