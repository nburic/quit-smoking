package com.example.sampleapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.GoalDialogFragment
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.repo.AppRepo
import com.example.sampleapp.util.DateConverters
import com.example.sampleapp.util.DateConverters.calculateDifference
import com.example.sampleapp.util.DateConverters.calculateDifferenceToDays
import com.example.sampleapp.util.DateConverters.daysToDuration
import com.example.sampleapp.util.DateConverters.getTimestamp
import com.example.sampleapp.util.DateConverters.yearsToDays
import kotlinx.coroutines.launch


class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo
    internal val user: LiveData<User>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repo = AppRepo(userDao)
        user = repo.user
    }

    private fun calculateMoneyPerDay(smokedPerDay: Int, packPrice: Float, packCount: Int): Float {
        return (smokedPerDay.toFloat() / packCount.toFloat()) * packPrice
    }

    fun calculateSavedMoney(): Float? {
        user.value?.let {
            if (it.perDay == null || it.price == null || it.inPack == null) {
                return null
            }
            val days = calculateDifferenceToDays(it.date) ?: return null
            val moneyPerDay = calculateMoneyPerDay(it.perDay, it.price, it.inPack)
            return days * moneyPerDay
        }
        return null
    }

    fun calculateSpentMoney(): Float? {
        user.value?.let {
            if (it.perDay == null || it.price == null || it.inPack == null || it.years == null) {
                return null
            }
            val days = yearsToDays(it.years)
            val moneyPerDay = calculateMoneyPerDay(it.perDay, it.price, it.inPack)
            return days * moneyPerDay
        }
        return null
    }

    fun calculateLifeLost(smoked: Float?): String? {
        smoked ?: return null

        val days = smoked * 11 / 60 / 24
        return daysToDuration(days.toInt())
    }

    fun calculateLifeRegained(notSmoked: Float?): String? {
        notSmoked ?: return null

        val days = notSmoked * 11 / 60 / 24
        return daysToDuration(days.toInt())
    }

    fun calculateNotSmoked(): Float? {
        user.value?.let {
            if (it.perDay == null) {
                return null
            }

            val days = calculateDifferenceToDays(it.date) ?: return null
            return days.toFloat() * it.perDay.toFloat()
        }
        return null
    }

    fun calculateSmoked(): Float? {
        user.value?.let {
            if (it.years == null || it.perDay == null) {
                return null
            }

            val days = yearsToDays(it.years)
            return days * it.perDay.toFloat()
        }
        return null
    }

    fun setDifference(timestamp: Long?): String? {
        return calculateDifference(timestamp)
    }

    fun setGoal(position: Int) {
        viewModelScope.launch {
            val timestamp = getGoalTimestamp(position)
            timestamp?.let {
                repo.updateGoal(it, position)
            }
        }
    }

    private fun getGoalTimestamp(index: Int): Long? {
        user.value?.date?.let { startDate ->
            return when (index) {
                0 -> getTimestamp(startDate, 2, DateConverters.Duration.DAYS)
                1 -> getTimestamp(startDate, 3, DateConverters.Duration.DAYS)
                2 -> getTimestamp(startDate, 4, DateConverters.Duration.DAYS)
                3 -> getTimestamp(startDate, 5, DateConverters.Duration.DAYS)
                4 -> getTimestamp(startDate, 6, DateConverters.Duration.DAYS)
                5 -> getTimestamp(startDate, 1, DateConverters.Duration.WEEKS)
                6 -> getTimestamp(startDate, 10, DateConverters.Duration.DAYS)
                7 -> getTimestamp(startDate, 2, DateConverters.Duration.WEEKS)
                8 -> getTimestamp(startDate, 3, DateConverters.Duration.WEEKS)
                9 -> getTimestamp(startDate, 1, DateConverters.Duration.MONTHS)
                10 -> getTimestamp(startDate, 3, DateConverters.Duration.MONTHS)
                11 -> getTimestamp(startDate, 6, DateConverters.Duration.MONTHS)
                12 -> getTimestamp(startDate, 1, DateConverters.Duration.YEARS)
                13 -> getTimestamp(startDate, 5, DateConverters.Duration.YEARS)
                else -> null
            }
        }
        return null
    }

    fun getGoalPercentage(): Int? {
        var startDate: Long? = null
        var goalDate: Long? = null

        user.value?.date?.let { startDate = it / 1000 }
        user.value?.goal?.let { goalDate = it / 1000 }

        if (startDate == null || goalDate == null) {
            return null
        }

        val limit = goalDate!! - startDate!!
        val current = System.currentTimeMillis() / 1000 - startDate!!
        val percent = (current.toDouble() / limit.toDouble())
        val progress = percent * 100
        return progress.toInt()
    }

    fun getGoalPercentageText(): String? {
        var startDate: Long? = null
        var goalDate: Long? = null

        user.value?.date?.let { startDate = it / 1000 }
        user.value?.goal?.let { goalDate = it / 1000 }

        if (startDate == null || goalDate == null) {
            return null
        }

        val limit = goalDate!! - startDate!!
        val current = System.currentTimeMillis() / 1000 - startDate!!
        val percent = (current.toDouble() / limit.toDouble())
        val progress = (percent * 100).toFloat()

        return when (progress >= 100) {
            true -> "100%"
            false -> "%.1f".format(progress) + "%"
        }
    }
}