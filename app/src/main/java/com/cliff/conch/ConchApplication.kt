package com.cliff.conch

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.cliff.eventbuskotlin.MyEventBusIndex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus

class ConchApplication : MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        under28Init()
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d("ConchApplication OnCreate")
        initEventBus()
    }

    @Suppress("KotlinConstantConditions")
    private fun under28Init() {
        if (BuildConfig.FLAVOR == "minSdk28") return
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return BuildConfig.DEBUG
                return true
            }
        })
    }

    // 这里是EventBus3.0改版之后必须要设置的步骤，只有这样EventBus注解生成的SubscriberInfoIndex才能起作用
    // 一般在Application种初始化的时候调用
    private fun initEventBus() {
        EventBus.builder()
            .addIndex(MyEventBusIndex())
            .installDefaultEventBus()
    }
}