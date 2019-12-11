package com.example.sampleapp

import android.app.Application
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.repo.AppRepo
import timber.log.Timber


class MainApplication: Application() {

    companion object {
        const val GOAL_JOB_ID = 0
    }

    private lateinit var repository: AppRepo

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        repository = AppRepo(AppDatabase.getDatabase(this).userDao())
    }
}