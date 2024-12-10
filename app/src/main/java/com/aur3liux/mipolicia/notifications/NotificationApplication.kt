package com.aur3liux.naats.notifications

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.qualifiers.ApplicationContext

class NotificationApplication: Application() {

    @SuppressLint("ServiceCast")
    override fun onCreate() {
        super.onCreate()
        val notificationChannel = NotificationChannel(
            "notification_channel_id",
            "Notification name",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Setting up the channel
        notificationManager.createNotificationChannel(notificationChannel)
    }
}