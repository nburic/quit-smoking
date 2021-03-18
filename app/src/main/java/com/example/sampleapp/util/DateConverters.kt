package com.example.sampleapp.util

import android.content.Context
import com.example.sampleapp.R
import java.text.SimpleDateFormat
import java.util.*


object DateConverters {

    enum class Duration {
        MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS
    }


    private fun formatDate(context: Context): SimpleDateFormat {
        return SimpleDateFormat(context.getString(R.string.common_date_time_formatting_date_day_month_year), Locale.ROOT)
    }

    private fun formatTimeHoursMinutes(context: Context): SimpleDateFormat {
        return SimpleDateFormat(context.getString(R.string.common_date_time_formatting_time_hour_minute), Locale.ROOT)
    }

    private fun formatDateTime(context: Context): SimpleDateFormat {
        return SimpleDateFormat("${context.getString(R.string.common_date_time_formatting_date_day_month_year)} ${context.getString(R.string.common_date_time_formatting_time_hour_minute)}", Locale.ROOT)
    }

    fun toDateTime(context: Context, epoch: Long): String {
        return formatDateTime(context).format(epoch)
    }

    fun daysToDuration(days: Int): String {
        val mYears = days / 30 / 12
        val mMonths = days / 30

        return when {
            mYears > 0 -> {
                val mDays = days / 30 % 12
                "${mYears}y ${mMonths}m ${mDays}d"
            }
            mMonths > 0 -> {
                val mDays = days % 30
                "${mMonths}m ${mDays}d"
            }
            else -> "${days}d"
        }
    }

    fun calculateDifference(timestamp: Long?): String {
        timestamp ?: return String.empty

        val diff = System.currentTimeMillis() - timestamp
        val c = Calendar.getInstance()
        c.timeInMillis = diff

        val mYear = c.get(Calendar.YEAR) - 1970
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH) - 1
        val mHours = c.get(Calendar.HOUR)
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
     * Returns progress from 0 to 100 from start to end timestamp
     */
    fun getProgress(startDate: Long, endDate: Long): Int {
        val limit = endDate - startDate
        val current = System.currentTimeMillis() - startDate
        val percent = (current.toDouble() / limit.toDouble())
        val progress = percent * 100
        return progress.toInt()
    }

    /**
     * Calculates new timestamp from starting timestamp and duration
     */
    fun getEndTimestamp(start: Long, duration: Int, type: Duration): Long {
        val c = Calendar.getInstance()
        c.timeInMillis = start

        return when (type) {
            Duration.MINUTES -> {
                c.add(Calendar.MINUTE, duration)
                c.timeInMillis
            }
            Duration.HOURS -> {
                c.add(Calendar.HOUR, duration)
                c.timeInMillis
            }
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