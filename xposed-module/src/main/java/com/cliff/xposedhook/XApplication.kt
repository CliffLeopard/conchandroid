package com.cliff.xposedhook

import android.util.Log
import com.highcapable.yukihookapi.hook.xposed.application.ModuleApplication

class XApplication : ModuleApplication() {
    override fun onCreate() {
        super.onCreate()
        Log.e("GGL", "I am running in module space")
    }
}