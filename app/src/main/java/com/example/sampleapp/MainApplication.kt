package com.example.sampleapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.repo.AppRepo
import timber.log.Timber
import android.content.Intent
import android.app.PendingIntent

class MainApplication: Application() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
        const val NOTIFICATION_ID = 10000
    }

    private lateinit var repository: AppRepo

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        repository = AppRepo(AppDatabase.getDatabase(this).userDao())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()

            val notificationIntent = Intent(this, MainActivity::class.java)
            val notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("[Goal achieved!]")
                .setContentText("[Smoke free for 1 year]")
                .setSmallIcon(R.drawable.ic_notification_goal_done)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .build()

            val manager = getSystemService(NotificationManager::class.java)
            manager.notify(NOTIFICATION_ID, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "[CHANNEL GOAL]", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "[This is channel goal]"
        channel.enableLights(true)
        channel.lightColor = Color.YELLOW

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}