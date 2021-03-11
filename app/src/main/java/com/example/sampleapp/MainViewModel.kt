package com.example.sampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.UserEntity
import com.example.sampleapp.repo.AppRepo
import com.example.sampleapp.util.DateConverters
import com.example.sampleapp.util.DateConverters.calculateDifferenceToDays


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo

    internal val userEntity: LiveData<UserEntity>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repo = AppRepo(userDao)
        userEntity = repo.userEntity
    }

    fun calculateNotSmoked(): Int {
        userEntity.value?.let {
            val days = calculateDifferenceToDays(it.start) ?: return 0

            return (days.toFloat() * it.cigPerDay.toFloat()).toInt()
        }
        return 0
    }

    fun getSmokeFreeDays(): Int {
        val startDate = userEntity.value?.start ?: return 0
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