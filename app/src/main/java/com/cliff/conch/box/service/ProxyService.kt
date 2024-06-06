package com.cliff.conch.box.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.cliff.conch.R
import com.orhanobut.logger.Logger


class ProxyService : Service() {
    @SuppressLint("WrongConstant")
    override fun onCreate() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            "ProxyServiceChannel",
            NotificationManager.IMPORTANCE_HIGH
        )
        manager.createNotificationChannel(channel)
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID.toString())
        builder.setContentTitle("ProxyService") //指定通知栏的标题内容
            .setContentText("后台代理服务正在运行") //通知的正文内容
            .setWhen(System.currentTimeMillis()) //通知创建的时间
            .setSmallIcon(R.drawable.ic_launcher_background) //通知显示的小图标，只能用alpha图层的图片进行设置
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_launcher_background
                )
            )

        val notification: Notification = builder.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Logger.i("Service开启通知: ${Build.VERSION.SDK_INT}")
            startForeground(1, notification, FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            Logger.i("Service开启通知2: ${Build.VERSION.SDK_INT}")
            startForeground(1, notification)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val CHANNEL_ID = "ProxyService"
    }
}