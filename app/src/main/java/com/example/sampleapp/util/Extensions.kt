package com.example.sampleapp.util

import android.view.View
import java.util.*


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

object Epoch {
    fun now(): Long {
        return System.currentTimeMillis()
    }

    fun calcPercentage(start: Long, end: Long): Float {
        val limit = end - start
        val current = now() - start
        val percent = (current.toFloat() / limit.toFloat())
        val progress = percent * 100

        return when (progress >= 100) {
            true -> 100f
            false -> progress
        }
    }

    fun calcSmoked(years: Int, perDay: Int): Int {
        val days = years * 365
        return days * perDay
    }

    fun calcNotSmoked(start: Long, perDay: Int): Int {
        val days = calcDifferenceToDays(start)
        return days * perDay
    }

    fun calcMoney(days: Int, perDay: Int, inPack: Int, price: Float): Int {
        val moneyPerDay = (perDay.toFloat() / inPack.toFloat()) * price
        return (days * moneyPerDay).toInt()
    }

    fun calcLifeLost(smoked: Int): String {
        val days = smoked * 11 / 60 / 24
        return DateConverters.daysToDuration(days)
    }

    fun calcLifeRegained(notSmoked: Int): String {
        val days = notSmoked * 11 / 60 / 24
        return DateConverters.daysToDuration(days)
    }

    /**
     * Calculates difference from now to past date in days
     */
    fun calcDifferenceToDays(start: Long): Int {
        val diff = now() - start
        val c = Calendar.getInstance()
        c.timeInMillis = diff

        val mYear = c.get(Calendar.YEAR) - 1970
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH) - 1

        var days = 0

        when {
            mYear > 0 -> days += mYear * 365
            mMonth > 0 -> days += mMonth * 30
            mDay > 0 -> days += mDay
        }

        return days
    }

    fun calcPassedTime(timestamp: Long): String {
        val diff = now() - timestamp
        val c = Calendar.getInstance()
        c.timeInMillis = diff

        val mYear = c.get(Calendar.YEAR) - 1970
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH) - 1
        val mHours = c.get(Calendar.HOUR_OF_DAY)
        val mMinutes = c.get(Calendar.MINUTE)
        val mSeconds = c.get(Calendar.SECOND)

        return when {
            mYear > 0 -> "${mYear}y ${mMonth}m ${mDay}d"
            mMonth > 0 -> "${mMonth}m ${mDay}d ${mHours}h"
            mDay > 0 -> "${mDay}d ${mHours}h ${mMinutes}min"
            mHours > 0 -> "${mHours}h ${mMinutes}min ${mSeconds}s"
            mMinutes > 0 -> "${mMinutes}min ${mSeconds}s"
            else -> "${mSeconds}s"
        }
    }

}