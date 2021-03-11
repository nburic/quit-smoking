package com.example.sampleapp.ui.health

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.UserEntity
import com.example.sampleapp.repo.AppRepo


class HealthViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo
    internal val userEntity: LiveData<UserEntity>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repo = AppRepo(userDao)
        userEntity = repo.userEntity
    }

}