package com.example.sampleapp.ui.views.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.sampleapp.R


class ProgressCardView : CardView {

    private lateinit var tvProgressValue: TextView
    private lateinit var tvGoalPercentage: TextView
    private lateinit var tvGoalValue: TextView
    private lateinit var seekBar: SeekBar

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
        tvGoalValue.setOnClickListener { onSelectGoalClick() }

        seekBar.isEnabled = false
    }

    fun setProgressValue(value: String) {
        tvProgressValue.text = value
    }

    @SuppressLint("SetTextI18n")
    fun setGoalValue(value: String) {
        tvGoalValue.text = "${context.resources.getString(R.string.goal_title)} $value"
    }

    fun setGoalPercentage(progress: Float) {
        setSeekBarValue(progress.toInt())
        setGoalPercentageValue(progress)
    }

    private fun setSeekBarValue(progress: Int) {
        ObjectAnimator.ofInt(seekBar, "progress", seekBar.progress, progress).apply {
            duration = 1000
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setGoalPercentageValue(progress: Float) {
        tvGoalPercentage.text = when (progress >= 100) {
            true -> "100%"
            false -> "%.1f".format(progress) + "%"
        }

    }
}