package com.cliff.conch.start

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import com.orhanobut.logger.Logger

class CliffWorkManagerInitializer : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        if (!WorkManager.isInitialized()) {
            val configuration = Configuration.Builder().build()
            WorkManager.initialize(context, configuration)
        }
        Logger.i("CliffWorkManagerInitializer create")
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        Logger.i("CliffWorkManagerInitializer dependencies")
        return emptyList()
    }

}