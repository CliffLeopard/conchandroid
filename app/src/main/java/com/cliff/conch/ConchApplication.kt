package com.cliff.conch

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class ConchApplication : MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        under28Init()
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d("ConchApplication OnCreate")
    }

    @Suppress("KotlinConstantConditions")
    private fun under28Init() {
        if (BuildConfig.FLAVOR == "minSdk28") return
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}