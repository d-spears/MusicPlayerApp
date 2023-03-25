package com.example.musicplayerapp

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder

class MusicForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, Notification()) // Start foreground service with an empty notification
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true) // Stop foreground service
    }
}
