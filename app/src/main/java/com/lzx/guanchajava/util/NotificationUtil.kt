package com.lzx.guanchajava.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.lzx.guanchajava.R

const val CHANNEL_ID = "NOTIFICATION"
object NotificationUtil {
    fun showNotification(id: Int, channelName: String, title: String, content: String, intent: PendingIntent) {
        val manager = App.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(manager, channelName)

        val builder = createNotificationBuilder(title, content, intent)

        manager.notify(id, builder.build())
    }

    private fun createNotificationBuilder(title: String, content: String, intent: PendingIntent) =
                NotificationCompat.Builder(App.context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(intent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(manager: NotificationManager, channelName: String) {
        val channel = NotificationChannel(channelName, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        manager.createNotificationChannel(channel)

    }


}