package com.cliff.conch.box.service.helper

import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ATMSScheduler {
    const val NOT_SCHEDULE = -1000
    fun scheduleStartActivity(intent: Intent?): Int {
        if ("application/vnd.android.package-archive" == intent?.type && intent.data != null)
            installApk(intent.data!!)
        return NOT_SCHEDULE
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun installApk(uri: Uri) {
        GlobalScope.launch {
        }
    }
}