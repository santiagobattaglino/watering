package com.santiago.battaglino.esp32.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import com.santiago.battaglino.esp32.R

object NotificationUtil {

    const val FG_SERVICE_CHANNEL = "forground_service_channel"

    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel
        val name = context.getString(R.string.fg_channel_name)
        val descriptionText = context.getString(R.string.fg_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(FG_SERVICE_CHANNEL, name, importance)
        channel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}