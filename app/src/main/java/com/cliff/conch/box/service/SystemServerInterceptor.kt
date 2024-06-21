package com.cliff.conch.box.service

import android.annotation.SuppressLint
import android.os.RemoteException
import com.cliff.conch.box.service.client.ClientProxyHandlerATMS
import com.cliff.conch.box.service.server.OriginServerManager
import com.cliff.conch.reflect.PActivityTaskManagerImpl
import com.cliff.conch.reflect.PSingletonImpl
import java.lang.reflect.Proxy

object SystemServerInterceptor {
    @SuppressLint("PrivateApi")
    @Throws(RemoteException::class, ClassNotFoundException::class)
    fun interceptATMS() {
        val atmsProxy = ProxyServiceManager.getATMS()
        val originATMS = OriginServerManager.getOriginATMS()

        val interfaces = originATMS.javaClass.interfaces
        val proxy = Proxy.newProxyInstance(
            originATMS.javaClass.classLoader,
            interfaces,
            ClientProxyHandlerATMS(originATMS, atmsProxy)
        )
        val single: Any = PActivityTaskManagerImpl.IActivityTaskManagerSingleton_s_get_()
        PSingletonImpl.mInstance_o_set_(single, proxy)
    }
}