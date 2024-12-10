package com.aur3liux.naats.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.aur3liux.naats.R
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    // SIMPLE NOTIFICATION
    fun showSimpleNotification(title: String, body: String) {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.icon_notification)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()  // finalizes the creation

        notificationManager.notify(Random.nextInt(), notification)
    }
}