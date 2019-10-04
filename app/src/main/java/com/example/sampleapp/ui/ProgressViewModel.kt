package com.example.sampleapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.repo.AppRepo
import com.example.sampleapp.views.ProgressCardView
import java.time.Duration
import java.time.LocalDateTime
import java.util.*


class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo
    internal val user: LiveData<User>

    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        repo = AppRepo(userDao)
        user = repo.user
//        val v = ProgressCardView(application.applicationContext)
//
//        val date1 = user.value?.date
//        val date2 = System.currentTimeMillis()


    }

    fun calculateDifference(timestamp: Long): String {

        val diff = System.currentTimeMillis() - timestamp

//        val day = (1000 * 60 * 60 * 24).toLong() // 24 hours in milliseconds
//        val time = day * 39 // for example, 39 days

        val c = Calendar.getInstance()
        c.timeInMillis = diff
        val mYear = c.get(Calendar.YEAR) - 1970
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH) - 1
        val mWeek = (c.get(Calendar.DAY_OF_MONTH) - 1) / 7

        return "${mYear}y ${mMonth}m ${mDay}d ${mWeek}w"
    }
}