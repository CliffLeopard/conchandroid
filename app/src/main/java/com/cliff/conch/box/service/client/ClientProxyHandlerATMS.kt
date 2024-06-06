package com.cliff.conch.box.service.client

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.cliff.conch.box.service.IActivityTaskManager
import com.orhanobut.logger.Logger
import java.lang.reflect.Method

// 客户端代理，在客户端进程执行
class ClientProxyHandlerATMS(
    private val originATMS: Any,
    private val proxyATMS: IActivityTaskManager
) : ClientInvocationHandler {
    @Throws(Throwable::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
        Logger.i("ClientProxyActivityTaskManger:" + method.name)
        return when (method.name) {
            // 进入代理服务
            "startActivity" ->
                proxyATMS.startActivity(
                    args[1] as? String,
                    args[2] as? String,
                    args[3] as? Intent,
                    args[4] as? String,
                    args[5] as? IBinder,
                    args[6] as? String,
                    args[7] as Int,
                    args[8] as Int,
                    args[10] as? Bundle
                )

            "startActivityAsUser" ->
                proxyATMS.startActivityAsUser(
                    args[1] as? String,
                    args[2] as? String,
                    args[3] as? Intent,
                    args[4] as? String,
                    args[5] as? IBinder,
                    args[6] as? String,
                    args[7] as Int,
                    args[8] as Int,
                    args[9] as? Bundle,
                    args[10] as Int
                )
            // 进入原系统服务
            else -> method.invoke(originATMS, *args)
        }
    }
}