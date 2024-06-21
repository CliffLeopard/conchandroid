package com.cliff.conch.box.service

import android.os.Bundle
import android.os.Debug.waitForDebugger
import android.os.IBinder
import android.util.ArrayMap
import com.cliff.conch.box.service.ServerConst.BUNDLE_BACK_KEY
import com.cliff.conch.box.service.ServerConst.BUNDLE_KEY
import com.cliff.conch.box.service.ServerConst.CLEAN_SERVICE
import com.cliff.conch.box.service.ServerConst.GET_SERVICE
import com.cliff.conch.box.service.ServerConst.NAME_ATMS
import com.cliff.conch.box.service.ServerConst.REGISTER_SERVICE
import com.cliff.conch.box.service.ServerConst.REMOVE_SERVICE
import com.cliff.conch.box.service.server.ServerProxyATMS
import com.orhanobut.logger.Logger

class ProxyServiceProvider : SProvider() {
    private val serviceFetcher by lazy {
        object : ServiceFetcher.Stub() {
            override fun getService(name: String?): IBinder? {
                return proxyStub[name]
            }

            // 其它进程注册服务
            override fun registService(name: String, service: IBinder) {
                proxyStub[name] = service
            }

            override fun removeService(name: String?) {
                proxyStub.remove(name)
            }

            override fun cleanService() {
                proxyStub.clear()
            }
        }
    }

    // 它的执行，肯定会早于系统的bindApplication,所以需要不能在这里Hook
    override fun onCreate(): Boolean {
        Logger.i("SystemServerProxyProvider:onCreate")
        prepareSystemServer()
        return true
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        Logger.i("SystemServerProxyProvider:call()  $method  $arg")
        return when (method) {
            GET_SERVICE -> {
                val bundle = Bundle()
                // 这里有两种方式，第一种目前这样，直接返回serviceFetcher，这样客户端拿到之后asInterface之后，再调用getService获取代理服务
                // 另一种方式，直接返回serviceFetcher.getService(arg),这样，客户端传递服务名称，不需要再getService获取代理服务；都可以
                // 因为第二张方式需要多次调用acquireContentProviderClient，所以目前采用第一种方式；
                bundle.putBinder(BUNDLE_BACK_KEY, serviceFetcher)
                bundle
            }

            REGISTER_SERVICE -> {
                if (!arg.isNullOrBlank() && extras != null) {
                    extras.getBinder(BUNDLE_KEY)?.let {
                        serviceFetcher.registService(arg, it)
                    }
                }
                null
            }

            REMOVE_SERVICE -> {
                serviceFetcher.removeService(arg)
                null
            }

            CLEAN_SERVICE -> {
                serviceFetcher.cleanService()
                null
            }

            else -> null
        }
    }

    private fun prepareSystemServer() {
        if (proxyStub.isNotEmpty()) return
        Logger.i("SystemServerProxyProvider:prepareSystemServer")
        proxyStub[NAME_ATMS] = ServerProxyATMS()
    }

    companion object {
        private val proxyStub = ArrayMap<String, IBinder>()
    }
}