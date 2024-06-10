package com.cliff.conch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.orhanobut.logger.Logger

object BroadCastRegister {
    private val context by lazy { ConchApplication.context }
    private val installReceiver by lazy { InstallReceiver() }
    fun registerApkInstall() {
        Logger.i("BroadCastRegister:registerApkInstall")
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED)
        intentFilter.addDataScheme("package")
        context.registerReceiver(installReceiver, intentFilter)
    }

    fun unRegisterApkInstall(receiver: InstallReceiver) {
        Logger.i("BroadCastRegister:unRegisterApkInstall")
        context.unregisterReceiver(receiver)
    }
}

class InstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.i("BroadCastRegister ${intent?.action ?: "NO MESSAGE"}")
    }
}