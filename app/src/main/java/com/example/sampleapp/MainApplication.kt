package com.example.sampleapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        const val GOAL_NOTIFICATION_ID = 0
        const val GOAL_CHANNEL_ID = "GOAL_CHANNEL_ID"
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}