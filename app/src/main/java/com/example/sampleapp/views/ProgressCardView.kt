package com.example.sampleapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.sampleapp.R

class ProgressCardView : CardView {

    private lateinit var tvProgressValue: TextView
    lateinit var ivSetGoal: ImageView


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val view = View.inflate(context, R.layout.mp_card_progress, this)

        tvProgressValue = view.findViewById(R.id.tv_progress_value)
        ivSetGoal = view.findViewById(R.id.iv_set_goal)
    }

    fun setProgressValue(value: String?) {
        value ?: return
        tvProgressValue.text = value
    }
}