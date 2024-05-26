package com.cliff.conch.box.service

import android.annotation.SuppressLint
import android.net.Uri
import com.cliff.conch.ConchApplication
import com.orhanobut.logger.Logger

@SuppressLint("StaticFieldLeak")
object FakeActivityTaskManager {
    private val context = ConchApplication.context
    fun getService(): IActivityTaskManager {
        Logger.d("FakeActivityTaskManager:getService()")
        val contentProviderClient =
            context.contentResolver.acquireContentProviderClient(Uri.parse("content://${ServerConst.SEVER_AUTHORITY}"))
        val bundle = contentProviderClient?.call(
            FakeSystemServerProvider.GET_SERVICE,
            FakeSystemServerProvider.SERVICE_ACTIVITY_TASK_MANAGER_SERVICE,
            null
        )
        val binder = bundle?.getBinder(FakeSystemServerProvider.BUNDLE_BACK_KEY)
        return IActivityTaskManager.Stub.asInterface(binder)
    }
}