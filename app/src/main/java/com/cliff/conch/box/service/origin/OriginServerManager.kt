package com.cliff.conch.box.service.origin

import android.annotation.SuppressLint
import android.os.IBinder
import com.cliff.conch.box.service.ServerConst
import reflect.android.app.StubReImpl
import reflect.android.os.ServiceManagerReImpl

object OriginServerManager {
    @SuppressLint("PrivateApi")
    fun getOriginATMS(): Any {
        val map = ServiceManagerReImpl.sCache_s_get_()
        val originBinderProxy: IBinder? = map[ServerConst.NAME_ATMS]
        return StubReImpl.asInterface(originBinderProxy!!)
    }
}