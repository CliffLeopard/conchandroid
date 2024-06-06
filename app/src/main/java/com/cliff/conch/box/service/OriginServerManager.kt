package com.cliff.conch.box.service

import android.annotation.SuppressLint
import com.cliff.conch.box.scene.ServiceManager
import org.lsposed.hiddenapibypass.HiddenApiBypass

object OriginServerManager {
    @SuppressLint("PrivateApi")
    fun getOriginATMS(): Any {
        val map = ServiceManager.sCache.get()
        val originBinderProxy: Any? = map[ServerConst.NAME_ATMS]
        val clzS = Class.forName("android.app.IActivityTaskManager\$Stub")
        return HiddenApiBypass.invoke(clzS, null, "asInterface", originBinderProxy)
    }
}