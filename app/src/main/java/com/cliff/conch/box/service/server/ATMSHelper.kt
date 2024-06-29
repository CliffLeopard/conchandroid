package com.cliff.conch.box.service.server

import android.content.Intent
import android.net.Uri

object ATMSHelper {
    const val NOT_SCHEDULE = -1000
    fun scheduleStartActivity(intent: Intent?): Int {
        if ("application/vnd.android.package-archive" == intent?.type && intent.data != null)
            installApk(intent.data!!)
        return NOT_SCHEDULE;
    }


    private fun installApk(uri: Uri) {

    }
}