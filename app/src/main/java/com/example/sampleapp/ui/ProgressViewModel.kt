package com.example.sampleapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.repo.AppRepo
import com.example.sampleapp.util.DateConverters.calculateDifference
import com.example.sampleapp.util.DateConverters.calculateDifferenceToDays


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

    fun setDifference(timestamp: Long?): String? {
        return calculateDifference(timestamp)
    }
}