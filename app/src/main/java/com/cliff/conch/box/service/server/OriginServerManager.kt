package com.cliff.conch.box.service.server

import android.annotation.SuppressLint
import android.os.IBinder
import com.cliff.conch.box.service.ServerConst
import com.cliff.conch.reflect.PServiceManagerImpl
import com.cliff.conch.reflect.StubImpl

object OriginServerManager {
    @SuppressLint("PrivateApi")
    fun getOriginATMS(): Any {
        val map = PServiceManagerImpl.sCache_s_get_()
        val originBinderProxy: IBinder? = map[ServerConst.NAME_ATMS]
        return StubImpl.asInterface(originBinderProxy!!)
    }
}