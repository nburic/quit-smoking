package com.example.sampleapp.ui.home.goal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.databinding.FragmentGoalListDialogItemBinding

class AdapterGoals(private var items: List<String> = emptyList(),
                   private var onItemSelected: (position: Int) -> Unit) : RecyclerView.Adapter<AdapterGoals.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentGoalListDialogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)
        holder.binding.root.setOnClickListener {
            onItemSelected.invoke(position)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: FragmentGoalListDialogItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvTitle.text = item
        }
    }
}