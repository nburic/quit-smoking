package com.example.sampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.models.SettingsInputItem
import com.example.sampleapp.models.SettingsInputItemType


class AdapterSettingsInput(private var onIncrement: ((SettingsInputItem) -> Unit)? = null,
                           private var onDecrement: ((SettingsInputItem) -> Unit)? = null) : RecyclerView.Adapter<AdapterSettingsInput.ViewHolder>() {

    private var items: List<SettingsInputItem> = listOf(
        SettingsInputItem("[Cigarettes smoked per day]", SettingsInputItemType.PER_DAY, null),
        SettingsInputItem("[Cigarettes in a pack]", SettingsInputItemType.IN_PACK, null),
        SettingsInputItem("[Years of smoking]", SettingsInputItemType.YEARS, null),
        SettingsInputItem("[Price per pack]", SettingsInputItemType.PRICE, null)
    )

    fun setItems(items: List<SettingsInputItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItems(): List<SettingsInputItem> {
        return items
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.btnInc.setOnClickListener {
            onIncrement?.invoke(item)
            notifyDataSetChanged()
        }


        holder.btnDec.setOnClickListener {
            onDecrement?.invoke(item)
            notifyDataSetChanged()
        }

        holder.bindData(item)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stg_input, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_input_title)
        private val tvValue: TextView = itemView.findViewById(R.id.tv_input_value)
        var btnInc: ImageButton = itemView.findViewById(R.id.btn_plus)
        var btnDec: ImageButton = itemView.findViewById(R.id.btn_minus)

        fun bindData(item: SettingsInputItem) {
            tvTitle.text = item.title

            when (item.value == null) {
                true -> tvValue.text = "0"
                false -> tvValue.text = item.value.toString()
            }

        }
    }
}