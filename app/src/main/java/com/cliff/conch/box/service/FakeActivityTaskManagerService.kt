package com.cliff.conch.box.service

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.orhanobut.logger.Logger

class FakeActivityTaskManagerService : IActivityTaskManager.Stub() {
    override fun startNextMatchingActivity(
        callingActivity: IBinder?,
        intent: Intent?,
        options: Bundle?
    ): Boolean {
        Logger.i("FakeActivityTaskManagerService:startNextMatchingActivity")
        return  true
    }
}