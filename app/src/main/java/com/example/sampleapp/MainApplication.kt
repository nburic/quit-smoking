package com.example.sampleapp

import android.app.Application
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.repo.AppRepo
import timber.log.Timber

class MainApplication: Application() {

    private lateinit var repository: AppRepo

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        repository = AppRepo(AppDatabase.getDatabase(this).userDao())
    }
}