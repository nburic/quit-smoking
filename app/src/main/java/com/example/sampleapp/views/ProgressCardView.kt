package com.example.sampleapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.sampleapp.R

class ProgressCardView : CardView {

    private lateinit var tvProgressValue: TextView
    private lateinit var tvGoalPercentage: TextView
    private lateinit var tvGoalValue: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var ivSetGoal: ImageView


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    var onSelectGoalClick: () -> Unit = { throw NotImplementedError("onSelectGoalClick not implemented") }

    private fun init() {
        val view = View.inflate(context, R.layout.mp_card_progress, this)

        seekBar = view.findViewById(R.id.seekBar)
        tvProgressValue = view.findViewById(R.id.tv_progress_value)
        tvGoalPercentage = view.findViewById(R.id.tv_goal_percentage)
        tvGoalValue = view.findViewById(R.id.tv_goal)
        ivSetGoal = view.findViewById(R.id.iv_set_goal)
        ivSetGoal.setOnClickListener { onSelectGoalClick() }

        seekBar.isEnabled = false
    }

    fun setProgressValue(value: String?) {
        value ?: return
        tvProgressValue.text = value
    }

    @SuppressLint("SetTextI18n")
    fun setGoalValue(value: String) {
        tvGoalValue.text = "${context.resources.getString(R.string.goal_title)} $value"
    }

    fun setSeekBarValue(progress: Int?) {
        when (progress == null) {
            true -> seekBar.progress = 0
            false -> seekBar.progress = progress
        }
    }

    fun setGoalPercentageValue(progress: String?) {
        when (progress == null) {
            true -> tvGoalPercentage.text = ""
            false -> tvGoalPercentage.text = progress
        }
    }
}