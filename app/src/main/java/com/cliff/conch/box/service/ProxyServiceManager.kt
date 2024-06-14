package com.cliff.conch.box.service

import android.net.Uri
import com.cliff.conch.ConchApplication
import com.cliff.wrapper.service.IActivityTaskManager
import com.cliff.wrapper.service.IPackageManager
import com.orhanobut.logger.Logger

object ProxyServiceManager {
    private val proxyProviderUri = Uri.parse("content://${ServerConst.SEVER_PROXY_AUTHORITY}")
    private val serviceFetcher by lazy {
        val contentProviderClient =
            ConchApplication.context.contentResolver.acquireContentProviderClient(proxyProviderUri)
        val bundle = contentProviderClient?.call(
            ServerConst.GET_SERVICE,
            null,
            null
        )
        contentProviderClient?.close()
        ServiceFetcher.Stub.asInterface(bundle?.getBinder(ServerConst.BUNDLE_BACK_KEY))
    }

    fun getATMS(): IActivityTaskManager {
        Logger.d("ServiceManagerProxy:getATMS()")
        return IActivityTaskManager.Stub.asInterface(serviceFetcher.getService(ServerConst.NAME_ATMS))
    }

    fun getPMS(): IPackageManager {
        Logger.d("ServiceManagerProxy:getPMS()")
        return IPackageManager.Stub.asInterface(serviceFetcher.getService(ServerConst.NAME_PMS))
    }
}