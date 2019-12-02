package com.example.sampleapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.sampleapp.MainActivity
import com.example.sampleapp.MainApplication
import com.example.sampleapp.R


class NotificationJobService: JobService() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
        const val NOTIFICATION_ID = 10000
    }

    private lateinit var notifyManager: NotificationManager

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
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

        notifyManager.notify(NOTIFICATION_ID, notification)

        return false
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifyManager = getSystemService(NotificationManager::class.java)

            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "[CHANNEL GOAL]", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "[This is channel goal]"
            channel.enableLights(true)
            channel.lightColor = Color.YELLOW

            notifyManager.createNotificationChannel(channel)
        }
    }
}