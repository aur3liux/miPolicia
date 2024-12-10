package com.aur3liux.mipolicia.notifications

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.aur3liux.mipolicia.R
import kotlin.random.Random

class NaatsNotificationService(
    private val context:Context
){
    private val notificationManager=context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification(){
        val notification=NotificationCompat.Builder(context,"water_notification")
            .setContentTitle("Water Reminder")
            .setContentText("Time to drink a glass of water")
            .setSmallIcon(R.drawable.icon_notification)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun showExpandableNotification(){
        val notification=NotificationCompat.Builder(context,"water_notification")
            .setContentTitle("Water Reminder")
            .setContentText("Time to drink a glass of water")
            .setSmallIcon(R.drawable.icon_notification)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)

            .build()
        notificationManager.notify(Random.nextInt(),notification)
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    )= BitmapFactory.decodeResource(
        resources,
        resId
    )
}