package com.example.sampleapp

import android.app.Application
import android.util.Log
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.repo.AppRepo

class MainApplication: Application() {

    private lateinit var repo: AppRepo

    override fun onCreate() {
        super.onCreate()

        val userDao = AppDatabase.getDatabase(this).userDao()
        repo = AppRepo(userDao)
        val user = repo.user

        if (user.value?.date == null) {
            Log.d("!!!", "DB not init")
        } else {
            Log.d("!!!", "DB init")
        }
    }
}