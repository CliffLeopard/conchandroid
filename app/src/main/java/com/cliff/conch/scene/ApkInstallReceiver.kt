package com.cliff.conch.scene

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cliff.conch.BroadCastRegister
import com.orhanobut.logger.Logger

class ApkInstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Logger.i("ApkInstallReceiver: ${intent.action ?: "NO Action"}")
    }
}