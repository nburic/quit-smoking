package com.example.sampleapp.models.health

import android.content.Context
import com.example.sampleapp.R
import com.example.sampleapp.util.DateConverters
import com.example.sampleapp.util.DateConverters.getEndTimestamp
import com.example.sampleapp.util.DateConverters.getProgress
import java.sql.Date


class HealthAchievementItem {

    internal var description: String = ""
    internal var finishDate: String = ""
    internal var progress: Int = 0

    fun setCardData(context: Context, index: Int, startDate: Long) {
        setDescription(context, index)
        setFinishDate(index, startDate)
        setProgress(index, startDate)
    }

    private fun setDescription(context: Context, index: Int) {
        when (index) {
            0 -> description = context.resources.getString(R.string.healt_descr_one)
            1 -> description = context.resources.getString(R.string.healt_descr_two)
            2 -> description = context.resources.getString(R.string.healt_descr_three)
            3 -> description = context.resources.getString(R.string.healt_descr_four)
            4 -> description = context.resources.getString(R.string.healt_descr_five)
            5 -> description = context.resources.getString(R.string.healt_descr_six)
            6 -> description = context.resources.getString(R.string.healt_descr_seven)
            7 -> description = context.resources.getString(R.string.healt_descr_eight)
            8 -> description = context.resources.getString(R.string.healt_descr_nine)
            9 -> description = context.resources.getString(R.string.healt_descr_ten)
            10 -> description = context.resources.getString(R.string.healt_descr_eleven)
            11 -> description = context.resources.getString(R.string.healt_descr_twelve)
            12 -> description = context.resources.getString(R.string.healt_descr_thirteen)
            else -> description = ""
        }
    }

    private fun setFinishDate(index: Int, startDate: Long) {
        this.finishDate = Date(getEndDate(index, startDate)).toString()
    }

    private fun getEndDate(index: Int, startDate: Long): Long {
        return when (index) {
            0 -> getEndTimestamp(startDate, 20, DateConverters.Duration.MINUTES)
            1 -> getEndTimestamp(startDate, 8, DateConverters.Duration.HOURS)
            2 -> getEndTimestamp(startDate, 24, DateConverters.Duration.HOURS)
            3 -> getEndTimestamp(startDate, 48, DateConverters.Duration.HOURS)
            4 -> getEndTimestamp(startDate, 72, DateConverters.Duration.HOURS)
            5 -> getEndTimestamp(startDate, 5, DateConverters.Duration.DAYS)
            6 -> getEndTimestamp(startDate, 10, DateConverters.Duration.DAYS)
            7 -> getEndTimestamp(startDate, 2, DateConverters.Duration.WEEKS)
            8 -> getEndTimestamp(startDate, 3, DateConverters.Duration.WEEKS)
            9 -> getEndTimestamp(startDate, 2, DateConverters.Duration.MONTHS)
            10 -> getEndTimestamp(startDate, 3, DateConverters.Duration.MONTHS)
            11 -> getEndTimestamp(startDate, 1, DateConverters.Duration.YEARS)
            12 -> getEndTimestamp(startDate, 10, DateConverters.Duration.YEARS)
            else -> 0
        }
    }

    private fun setProgress(index: Int, startDate: Long) {
        val endDate = getEndDate(index, startDate)
        this.progress = getProgress(startDate, endDate)
    }
}