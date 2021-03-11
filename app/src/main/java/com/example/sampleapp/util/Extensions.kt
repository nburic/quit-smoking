package com.example.sampleapp.util

import android.view.View


inline val String.Companion.empty: String
    get() = ""

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}