package com.example.sampleapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sampleapp.R


class SettingsInputView : ConstraintLayout {

    private lateinit var tvValue: TextView
    private lateinit var btnInc: ImageButton
    private lateinit var btnDec: ImageButton

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
        View.inflate(context, R.layout.stg_input, this)

        tvValue = findViewById(R.id.tv_input_value)
        btnInc = findViewById(R.id.btn_plus)
        btnInc.setOnClickListener {
            increaseValue()
        }

        btnDec = findViewById(R.id.btn_minus)
        btnDec.setOnClickListener {
            decreaseValue()
        }
    }

    fun getValue(): Int {
        return tvValue.text.toString().toInt()
    }

    fun setValue(value: Int?) {
        value ?: return
        tvValue.text = value.toString()
    }

    private fun increaseValue() {
        val currentValue = getValue() + 1
        tvValue.text = currentValue.toString()
    }

    private fun decreaseValue() {
        val currentValue = getValue()

        when (currentValue > 0) {
            true -> {
                val value = currentValue - 1
                tvValue.text = value.toString()
            }
            false -> {}
        }

    }
}