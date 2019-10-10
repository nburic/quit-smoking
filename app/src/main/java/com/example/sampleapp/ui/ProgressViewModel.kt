package com.example.sampleapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.repo.AppRepo
import java.util.*


class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo
    internal val user: LiveData<User>

    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        repo = AppRepo(userDao)
        user = repo.user
    }

    fun calculateDifference(timestamp: Long): String {
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
}