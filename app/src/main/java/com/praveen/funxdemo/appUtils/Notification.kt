package com.praveen.funxdemo.appUtils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.praveen.funxdemo.R
import com.praveen.funxdemo.appUI.HomeScreen.HomeActivity
import com.praveen.funxdemo.appUI.validDetails.ValidDetailsActivity
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import androidx.core.app.NotificationManagerCompat


object Notification {

    fun notification(mContent: Context, swapData: String) {
        val intent = Intent(mContent, HomeActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(mContent, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = NotificationChannel(
                ConstantKey.NOTIFICATION_CHANNEL_ID,
                "description",
                NotificationManager.IMPORTANCE_HIGH
            )
            notification.enableLights(true)
            notification.lightColor = Color.GREEN
            notification.enableVibration(false)
            val notificationManager = mContent.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notification)

            val builder = NotificationCompat.Builder(mContent, ConstantKey.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(mContent.getString(R.string.app_name))
                .setContentText("Fx value in Uniswap${swapData}")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
            notificationManager.notify(ConstantKey.NOTIFICATION_ID, builder.build())

        } else {
            val builder = NotificationCompat.Builder(mContent)
                .setContentTitle(mContent.getString(R.string.app_name))
                .setContentText("Fx value in Uniswap${swapData}")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
            val notificationManager = NotificationManagerCompat.from(mContent)
            notificationManager.notify(ConstantKey.NOTIFICATION_ID, builder.build())
        }
    }
}