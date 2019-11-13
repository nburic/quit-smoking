package com.example.sampleapp.ui.health

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.models.health.HealthAchievementItem


class AdapterCardsHealth(private var items: List<HealthAchievementItem>) : RecyclerView.Adapter<AdapterCardsHealth.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindData(item)
    }

    fun setItems(items: List<HealthAchievementItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mh_card_view, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val tvProgress: TextView = itemView.findViewById(R.id.tv_progress_value)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_task_status)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        @SuppressLint("SetTextI18n")
        fun bindData(item: HealthAchievementItem) {
            tvDescription.text = item.description

            val progress = item.progress
            tvProgress.text = "$progress%"

            when (progress == 100) {
                true -> tvStatus.text = "[Achieved]"
                false -> tvStatus.text = item.finishDate
            }

            progressBar.progress = progress
        }
    }
}