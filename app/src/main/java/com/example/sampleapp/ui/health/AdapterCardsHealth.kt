package com.example.sampleapp.ui.health

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.data.models.health.HealthAchievementItem
import com.example.sampleapp.databinding.MhCardViewBinding


class AdapterCardsHealth(private var items: List<HealthAchievementItem>) : RecyclerView.Adapter<AdapterCardsHealth.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MhCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindData(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: MhCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(item: HealthAchievementItem) {
            binding.tvDescription.text = item.description

            val progress = item.progress
            when (progress >= 100) {
                true -> {
                    binding.tvStatus.text = binding.root.context.resources.getString(R.string.health_status_done)
                    binding.tvProgressValue.text = "100%"
                }
                false -> {
                    binding.tvStatus.text = item.finishDate
                    binding.tvProgressValue.text = "$progress%"
                }

            }

            binding.progressBar.progress = progress
        }
    }
}