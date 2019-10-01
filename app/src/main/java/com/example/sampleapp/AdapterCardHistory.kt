package com.example.sampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.models.ProgressHistoryItem


class AdapterCardHistory(private val items: List<ProgressHistoryItem>) : RecyclerView.Adapter<AdapterCardHistory.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindData(item)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mp_item_history, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvValue: TextView = itemView.findViewById(R.id.tv_item_value)
        private val imgIcon: ImageView = itemView.findViewById(R.id.iv_item_icon)

        fun bindData(item: ProgressHistoryItem) {
            imgIcon.setImageResource(item.icon)
            tvValue.text = item.value
        }
    }
}