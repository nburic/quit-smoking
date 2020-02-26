package com.example.sampleapp.ui.health

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.models.health.HealthAchievementItem


class AdapterCardsHealth : RecyclerView.Adapter<AdapterCardsHealth.ViewHolder>() {

    private var items: List<HealthAchievementItem> = listOf()

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
        return ViewHolder(view, parent.context)
    }

    class ViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val tvProgress: TextView = itemView.findViewById(R.id.tv_progress_value)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_task_status)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        @SuppressLint("SetTextI18n")
        fun bindData(item: HealthAchievementItem) {
            tvDescription.text = item.description

            val progress = item.progress
            when (progress >= 100) {
                true -> {
                    tvStatus.text = context.resources.getString(R.string.health_status_done)
                    tvProgress.text = "100%"
                }
                false -> {
                    tvStatus.text = item.finishDate
                    tvProgress.text = "$progress%"
                }

            }

            progressBar.progress = progress
        }
    }
}