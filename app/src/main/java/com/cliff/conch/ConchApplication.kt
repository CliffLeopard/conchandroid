package com.cliff.conch

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.multidex.MultiDexApplication
import com.cliff.eventbuskotlin.MyEventBusIndex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import top.canyie.pine.Pine
import top.canyie.pine.Pine.CallFrame
import top.canyie.pine.PineConfig
import top.canyie.pine.callback.MethodHook


class ConchApplication : MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        hook()
        super.attachBaseContext(base)
        under28Init()
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d("ConchApplication OnCreate")
        initEventBus()
    }
}

@SuppressLint("PrivateApi", "SoonBlockedPrivateApi")
private fun hook() {
    PineConfig.debug = true; // 是否debug，true会输出较详细log
    PineConfig.debuggable = BuildConfig.DEBUG; // 该应用是否可调试，建议和配置文件中的值保持一致，否则会出现问题
    Pine.hook(
        Activity::class.java.getDeclaredMethod("onCreate", Bundle::class.java),
        Hooker("onCreate")
    )

    val clz = Class.forName("android.view.ViewRootImpl")
    val methodPerformMeasure =
        clz.getDeclaredMethod("performMeasure", Int::class.java, Int::class.java)
    val methodPerformLayout = clz.getDeclaredMethod(
        "performLayout",
        WindowManager.LayoutParams::class.java,
        Int::class.java,
        Int::class.java
    )
    val methodPerformDraw = clz.getDeclaredMethod("performDraw")
    val methodPerformTraversals = clz.getDeclaredMethod("performTraversals")
    val methodScheduleTraversals = clz.getDeclaredMethod("scheduleTraversals")
    val methodDoTraversal = clz.getDeclaredMethod("doTraversal")


//    Pine.hook(methodScheduleTraversals, Hooker("scheduleTraversals"))
//    Pine.hook(methodDoTraversal, Hooker("doTraversal"))
    Pine.hook(methodPerformTraversals, Hooker("performTraversals"))
    Pine.hook(methodPerformMeasure, Hooker("performMeasure"))
//    Pine.hook(methodPerformLayout, Hooker("performLayout"))
//    Pine.hook(methodPerformDraw, Hooker("performDraw"))


}

class Hooker(val methodName: String) : MethodHook() {
    override fun beforeCall(callFrame: CallFrame) {
        Logger.i("Before " + callFrame.thisObject + " $methodName()")
    }

    override fun afterCall(callFrame: CallFrame) {
        Logger.i("After " + callFrame.thisObject + "  $methodName()")
    }
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