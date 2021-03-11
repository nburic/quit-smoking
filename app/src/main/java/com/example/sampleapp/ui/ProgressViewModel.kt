package com.example.sampleapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.UserEntity
import com.example.sampleapp.repo.AppRepo
import com.example.sampleapp.util.DateConverters
import com.example.sampleapp.util.DateConverters.calculateDifference
import com.example.sampleapp.util.DateConverters.calculateDifferenceToDays
import com.example.sampleapp.util.DateConverters.daysToDuration
import com.example.sampleapp.util.DateConverters.getEndTimestamp
import com.example.sampleapp.util.DateConverters.yearsToDays
import kotlinx.coroutines.launch


class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo
    internal val userEntity: LiveData<UserEntity>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repo = AppRepo(userDao)
        userEntity = repo.userEntity
    }

    private fun calculateMoneyPerDay(smokedPerDay: Int, packPrice: Float, packCount: Int): Float {
        return (smokedPerDay.toFloat() / packCount.toFloat()) * packPrice
    }

    fun calculateSavedMoney(): Float? {
        userEntity.value?.let {
            val days = calculateDifferenceToDays(it.start) ?: return null
            val moneyPerDay = calculateMoneyPerDay(it.cigPerDay, it.price, it.inPack)
            return days * moneyPerDay
        }
        return null
    }

    fun calculateSpentMoney(): Float? {
        userEntity.value?.let {
            val days = yearsToDays(it.years.toFloat())
            val moneyPerDay = calculateMoneyPerDay(it.cigPerDay, it.price, it.inPack)
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
        userEntity.value?.let {
            if (it.cigPerDay == null) {
                return null
            }

            val days = calculateDifferenceToDays(it.start) ?: return null
            return days.toFloat() * it.cigPerDay.toFloat()
        }
        return null
    }

    fun calculateSmoked(): Float? {
        userEntity.value?.let {
            val days = yearsToDays(it.years.toFloat())
            return days * it.cigPerDay.toFloat()
        }
        return null
    }

    fun setDifference(timestamp: Long?): String {
        return calculateDifference(timestamp)
    }

    fun setGoal(position: Int) {
        viewModelScope.launch {
            val timestamp = getGoalTimestamp(position)
            timestamp?.let {
                repo.updateGoal(it)
            }
        }
    }

    private fun getGoalTimestamp(index: Int): Long? {
        userEntity.value?.start?.let { startDate ->
            return when (index) {
                0 -> getEndTimestamp(startDate, 2, DateConverters.Duration.DAYS)
                1 -> getEndTimestamp(startDate, 3, DateConverters.Duration.DAYS)
                2 -> getEndTimestamp(startDate, 4, DateConverters.Duration.DAYS)
                3 -> getEndTimestamp(startDate, 5, DateConverters.Duration.DAYS)
                4 -> getEndTimestamp(startDate, 6, DateConverters.Duration.DAYS)
                5 -> getEndTimestamp(startDate, 1, DateConverters.Duration.WEEKS)
                6 -> getEndTimestamp(startDate, 10, DateConverters.Duration.DAYS)
                7 -> getEndTimestamp(startDate, 2, DateConverters.Duration.WEEKS)
                8 -> getEndTimestamp(startDate, 3, DateConverters.Duration.WEEKS)
                9 -> getEndTimestamp(startDate, 1, DateConverters.Duration.MONTHS)
                10 -> getEndTimestamp(startDate, 3, DateConverters.Duration.MONTHS)
                11 -> getEndTimestamp(startDate, 6, DateConverters.Duration.MONTHS)
                12 -> getEndTimestamp(startDate, 1, DateConverters.Duration.YEARS)
                13 -> getEndTimestamp(startDate, 5, DateConverters.Duration.YEARS)
                else -> null
            }
        }
        return null
    }

    fun getGoalPercentage(): Float {
        var startDate: Long? = null
        var goalDate: Long? = null

        userEntity.value?.start?.let { startDate = it / 1000 }
        userEntity.value?.goal?.let { goalDate = it / 1000 }

        if (startDate == null || goalDate == null) {
            return 0f
        }

        val limit = goalDate!! - startDate!!
        val current = System.currentTimeMillis() / 1000 - startDate!!
        val percent = (current.toDouble() / limit.toDouble())
        val progress = (percent * 100).toFloat()

        return when (progress >= 100) {
            true -> 100f
            false -> progress
        }
    }
}