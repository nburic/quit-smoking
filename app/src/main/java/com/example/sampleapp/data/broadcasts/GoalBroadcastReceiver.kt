package com.example.sampleapp.data.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class GoalBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("Fire alarm notification!")
    }

}
