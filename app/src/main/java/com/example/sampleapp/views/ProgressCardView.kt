package com.example.sampleapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.sampleapp.R
import com.marcinmoskala.arcseekbar.ArcSeekBar

class ProgressCardView : CardView {

    private lateinit var tvProgressValue: TextView
    private lateinit var tvGoalValue: TextView
    private lateinit var seekBar: ArcSeekBar
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

        seekBar = view.findViewById(R.id.seekArc)
        tvProgressValue = view.findViewById(R.id.tv_progress_value)
        tvGoalValue = view.findViewById(R.id.tv_goal)
        ivSetGoal = view.findViewById(R.id.iv_set_goal)
    }

    fun setProgressValue(value: String?) {
        value ?: return
        tvProgressValue.text = value
    }

    @SuppressLint("SetTextI18n")
    fun setGoalValue(value: String) {
        tvGoalValue.text = "${context.resources.getString(R.string.goal_title)} $value"
    }

    fun setSeekBarValue(progress: Int) {
        seekBar.progress = progress
    }
}