package com.cliff.conch.box.service.server

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.cliff.conch.box.scene.ActivityThread
import com.cliff.conch.box.service.IActivityTaskManager
import com.cliff.conch.box.service.OriginServerManager.getOriginATMS
import com.orhanobut.logger.Logger
import org.lsposed.hiddenapibypass.HiddenApiBypass


// ProxyServer端创建的代理，在ProxyServer进程执行
class ServerProxyATMS : IActivityTaskManager.Stub() {
    private val originATMS by lazy {
        getOriginATMS()
    }

    override fun startActivity(
        callingPackage: String?,
        callingFeatureId: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        options: Bundle?
    ): Int {
        Logger.i("ProxyActivityTaskManager:startActivity")
        val activityThread = ActivityThread.currentActivityThread.call()
        val applicationThread = ActivityThread.mAppThread[activityThread]
        return HiddenApiBypass.invoke(
            originATMS.javaClass, originATMS, "startActivity",
            applicationThread,
            callingPackage,
            callingFeatureId,
            intent,
            resolvedType,
            resultTo,
            resultWho,
            requestCode,
            flags,
            null,
            options
        ) as Int
    }

    override fun startActivities(
        callingPackage: String?,
        callingFeatureId: String?,
        intents: Array<out Intent>?,
        resolvedTypes: Array<out String>?,
        resultTo: IBinder?,
        options: Bundle?,
        userId: Int
    ): Int {
        Logger.i("ProxyActivityTaskManager:startActivities")
        return 0
    }

    override fun startActivityAsUser(
        callingPackage: String?,
        callingFeatureId: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        options: Bundle?,
        userId: Int
    ): Int {
        Logger.i("ProxyActivityTaskManager:startActivityAsUser")
        return 0
    }

    override fun startNextMatchingActivity(
        callingActivity: IBinder?,
        intent: Intent?,
        options: Bundle?
    ): Boolean {
        Logger.i("ProxyActivityTaskManager:startNextMatchingActivity")
        return false
    }

    override fun startDreamActivity(intent: Intent?): Boolean {
        Logger.i("ProxyActivityTaskManager:startDreamActivity")
        return false
    }
}