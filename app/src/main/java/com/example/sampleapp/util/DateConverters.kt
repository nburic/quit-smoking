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

    fun getGoalTimestamp(position: Int, start: Long): Long {
        return when (position) {
            0 -> getEndTimestamp(start, 2, Duration.DAYS)
            1 -> getEndTimestamp(start, 3, Duration.DAYS)
            2 -> getEndTimestamp(start, 4, Duration.DAYS)
            3 -> getEndTimestamp(start, 5, Duration.DAYS)
            4 -> getEndTimestamp(start, 6, Duration.DAYS)
            5 -> getEndTimestamp(start, 1, Duration.WEEKS)
            6 -> getEndTimestamp(start, 10, Duration.DAYS)
            7 -> getEndTimestamp(start, 2, Duration.WEEKS)
            8 -> getEndTimestamp(start, 3, Duration.WEEKS)
            9 -> getEndTimestamp(start, 1, Duration.MONTHS)
            10 -> getEndTimestamp(start, 3, Duration.MONTHS)
            11 -> getEndTimestamp(start, 6, Duration.MONTHS)
            12 -> getEndTimestamp(start, 1, Duration.YEARS)
            13 -> getEndTimestamp(start, 5, Duration.YEARS)
            else -> 0L
        }
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
}