package com.cliff.conch.box.service

import android.annotation.SuppressLint
import android.os.RemoteException
import com.cliff.conch.box.service.client.ClientProxyHandlerATMS
import com.cliff.conch.box.service.origin.OriginServerManager
import reflect.android.app.ActivityTaskManager
import reflect.android.app.ActivityTaskManager__Functions.getIActivityTaskManagerSingleton
import reflect.android.util.Singleton
import reflect.android.util.Singleton__Functions.__instance__
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

        val single: Any =  ActivityTaskManager.getIActivityTaskManagerSingleton()!!
        Singleton.__instance__(single).mInstance = proxy
    }
}