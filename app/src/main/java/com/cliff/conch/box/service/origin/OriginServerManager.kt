package com.cliff.conch.box.service.origin

import android.annotation.SuppressLint
import android.os.IBinder
import com.cliff.conch.box.service.ServerConst
import reflect.android.app.IActivityTaskManager.Stub
import reflect.android.app.IActivityTaskManager_Stub__Functions.asInterface
import reflect.android.os.ServiceManager
import reflect.android.os.ServiceManager__Functions.getSCache

object OriginServerManager {
    @SuppressLint("PrivateApi")
    fun getOriginATMS(): Any {
        val map = ServiceManager.getSCache()
        val originBinderProxy: IBinder? = map[ServerConst.NAME_ATMS]
        return Stub.asInterface(originBinderProxy!!)
    }
}