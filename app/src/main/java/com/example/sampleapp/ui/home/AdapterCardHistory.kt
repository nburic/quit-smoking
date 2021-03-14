package com.example.sampleapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.data.models.home.ProgressHistoryItem


class AdapterCardHistory(private var items: List<ProgressHistoryItem>) : RecyclerView.Adapter<AdapterCardHistory.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindData(item)
    }

    fun setItems(items: List<ProgressHistoryItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mp_item_history, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rootView: ConstraintLayout = itemView.findViewById(R.id.root_view)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        private val tvValue: TextView = itemView.findViewById(R.id.tv_item_value)
        private val imgIcon: ImageView = itemView.findViewById(R.id.iv_item_icon)

        fun bindData(item: ProgressHistoryItem) {
            imgIcon.setImageResource(item.icon)
            tvTitle.text = item.title
            tvValue.text = item.value
        }
    }
}