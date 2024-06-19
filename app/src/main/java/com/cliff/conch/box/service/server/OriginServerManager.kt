package com.cliff.conch.box.service.server

import android.annotation.SuppressLint
import com.cliff.conch.box.scene.ServiceManager
import com.cliff.conch.box.service.ServerConst
import com.cliff.reflection.common.hidden.HiddenApi

object OriginServerManager {
    @SuppressLint("PrivateApi")
    fun getOriginATMS(): Any {
        val map = ServiceManager.sCache.get()
        val originBinderProxy: Any? = map[ServerConst.NAME_ATMS]
        val clzS = Class.forName("android.app.IActivityTaskManager\$Stub")
        return HiddenApi.invoke(clzS, null, "asInterface", originBinderProxy)!!
    }
}