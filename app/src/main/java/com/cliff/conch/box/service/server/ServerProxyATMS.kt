package com.cliff.conch.box.service.server

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.cliff.wrapper.service.PIAT
import com.cliff.wrapper.service.PProfilerInfo
import com.orhanobut.logger.Logger

// ProxyServer端创建的代理，在ProxyServer进程执行
class ServerProxyATMS : OriginATMS() {
    init {
        Logger.i("ServerProxyATMS:init")
    }

    override fun startActivity(
        caller: PIAT?,
        callingPackage: String?,
        callingFeatureId: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        pProfilerInfo: PProfilerInfo?,
        options: Bundle?
    ): Int {
        Logger.i("ProxyActivityTaskManager:startActivity")
        return super.startActivity(
            caller,
            callingPackage,
            callingFeatureId,
            intent,
            resolvedType,
            resultTo,
            resultWho,
            requestCode,
            flags,
            pProfilerInfo,
            options
        )
    }

    override fun startActivities(
        caller: PIAT?,
        callingPackage: String?,
        callingFeatureId: String?,
        intents: Array<out Intent>?,
        resolvedTypes: Array<out String>?,
        resultTo: IBinder?,
        options: Bundle?,
        userId: Int
    ): Int {
        Logger.i("ProxyActivityTaskManager:startActivities")
        return super.startActivities(
            caller,
            callingPackage,
            callingFeatureId,
            intents,
            resolvedTypes,
            resultTo,
            options,
            userId
        )
    }

    override fun startActivityAsUser(
        caller: PIAT?,
        callingPackage: String?,
        callingFeatureId: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        pProfilerInfo: PProfilerInfo?,
        options: Bundle?,
        userId: Int
    ): Int {
        Logger.i("ProxyActivityTaskManager:startActivityAsUser")
        return super.startActivityAsUser(
            caller,
            callingPackage,
            callingFeatureId,
            intent,
            resolvedType,
            resultTo,
            resultWho,
            requestCode,
            flags,
            pProfilerInfo,
            options,
            userId
        )
    }
//    override fun startNextMatchingActivity(
//        callingActivity: IBinder?,
//        intent: Intent?,
//        options: Bundle?
//    ): Boolean {
//        Logger.i("ProxyActivityTaskManager:startNextMatchingActivity")
//        return super.startNextMatchingActivity(callingActivity, intent, options)
//    }
//
//    override fun startDreamActivity(intent: Intent?): Boolean {
//        Logger.i("ProxyActivityTaskManager:startDreamActivity")
//        return super.startDreamActivity(intent)
//    }
}