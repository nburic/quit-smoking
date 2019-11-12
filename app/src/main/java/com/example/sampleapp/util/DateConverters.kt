package com.example.sampleapp.util

import androidx.room.TypeConverter
import java.sql.Date
import java.util.*


object DateConverters {

    enum class Duration {
        DAYS, WEEKS, MONTHS, YEARS
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date {
        return value?.let { Date(it) } ?: Date(0)
    }

    fun calculateDifference(timestamp: Long?): String? {
        timestamp ?: return null

        val diff = System.currentTimeMillis() - timestamp
        val c = Calendar.getInstance()
        c.timeInMillis = diff

        val mYear = c.get(Calendar.YEAR) - 1970
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH) - 1
        val mMinutes = c.get(Calendar.MINUTE)
        val mSeconds = c.get(Calendar.SECOND)

        return when {
            mYear > 0 -> "${mYear}y ${mMonth}m ${mDay}d"
            mMonth > 0 -> "${mMonth}m ${mDay}d ${mMinutes}min"
            mDay > 0 -> "${mDay}d ${mMinutes}min ${mSeconds}s"
            mMinutes > 0 -> "${mMinutes}min ${mSeconds}s"
            else -> "${mSeconds}s"
        }
    }

    /**
     * Calculates difference from now to past date in days
     */
    fun calculateDifferenceToDays(timestamp: Long?): Int? {
        timestamp ?: return null

        val diff = System.currentTimeMillis() - timestamp
        val c = Calendar.getInstance()
        c.timeInMillis = diff

        val mYear = c.get(Calendar.YEAR) - 1970
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH) - 1

        var days = 0

        when {
            mYear > 0 -> {
                days += mYear * 365
            }
            mMonth > 0 -> {
                days += mMonth * 30
            }
            mDay > 0 -> {
                days += mDay
            }
        }

        return days
    }

    /**
     * Calculates new timestamp from starting timestamp and duration
     */
    fun getTimestamp(start: Long, duration: Int, type: Duration): Long {
        val c = Calendar.getInstance()
        c.timeInMillis = start

        return when (type) {
            Duration.DAYS -> {
                c.add(Calendar.DATE, duration)
                c.timeInMillis
            }
            Duration.WEEKS -> {
                c.add(Calendar.WEEK_OF_YEAR, duration)
                c.timeInMillis
            }
            Duration.MONTHS -> {
                c.add(Calendar.MONTH, duration)
                c.timeInMillis
            }
            Duration.YEARS -> {
                c.add(Calendar.YEAR, duration)
                c.timeInMillis
            }
        }
    }

    fun yearsToDays(years: Float): Int {
        return (years * 365).toInt()
    }
}