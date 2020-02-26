package com.example.sampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.repo.AppRepo
import com.example.sampleapp.util.DateConverters
import com.example.sampleapp.util.DateConverters.calculateDifferenceToDays


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo

    internal val user: LiveData<User>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repo = AppRepo(userDao)
        user = repo.user
    }

    fun calculateNotSmoked(): Int {
        user.value?.let {
            if (it.perDay == null) {
                return 0
            }

            val days = DateConverters.calculateDifferenceToDays(it.date) ?: return 0

            return (days.toFloat() * it.perDay.toFloat()).toInt()
        }
        return 0
    }

    fun getSmokeFreeDays(): Int {
        val startDate = user.value?.date ?: return 0
        return calculateDifferenceToDays(startDate) ?: 0
    }

    fun getRegainedDays(): Int {
        val notSmoked = calculateNotSmoked()
        return calculateLifeRegained(notSmoked)
    }

    private fun calculateLifeRegained(notSmoked: Int): Int {
        return notSmoked * 11 / 60 / 24
    }
}