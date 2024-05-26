package com.cliff.conch.box.service

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import com.orhanobut.logger.Logger
import java.util.concurrent.ConcurrentHashMap

class FakeSystemServerProvider : ContentProvider() {
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    // 这里的所有方法都是为跨进程调用准备的，所以SystemServer进程自己注册，都不需要这样，直接添加就好
    private val serviceFetcher by lazy {
        object : ServiceFetcher.Stub() {
            override fun getService(name: String?): IBinder? {
                return services[name]
            }

            // 其它进程注册服务
            override fun registService(name: String, service: IBinder) {
                services[name] = service
            }

            override fun removeService(name: String?) {
                services.remove(name)
            }

            override fun cleanService() {
                services.clear();
            }
        }
    }

    override fun onCreate(): Boolean {
        services[SERVICE_ACTIVITY_TASK_MANAGER_SERVICE] = FakeActivityTaskManagerService()
        return true
    }

    // method:请求类型 arg:请求参数 extras:请求附带信息
    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        Logger.i("FakeSystemServerProvider:call()")
        return if (method == GET_SERVICE) {
            val binder: IBinder? = serviceFetcher.getService(arg)
            Bundle().apply {
                putBinder(BUNDLE_BACK_KEY, binder)
            }
        } else {
            when (method) {
                REGISTER_SERVICE -> {
                    if (!arg.isNullOrBlank() && extras != null) {
                        extras.getBinder(BUNDLE_KEY)?.let {
                            serviceFetcher.registService(arg, it)
                        }
                    }
                }

                REMOVE_SERVICE -> serviceFetcher.removeService(arg)
                CLEAN_SERVICE -> serviceFetcher.cleanService()
                else -> {}
            }
            null
        }
    }

    companion object {
        const val GET_SERVICE = "ss_gs"
        const val REGISTER_SERVICE = "ss_rs"
        const val REMOVE_SERVICE = "ss_rms"
        const val CLEAN_SERVICE = "ss_cls"
        const val BUNDLE_KEY = "ss_bk"
        const val BUNDLE_BACK_KEY = "ss_bbk"

        const val SERVICE_ACTIVITY_TASK_MANAGER_SERVICE = "ActivityTaskManagerService"
        private val services = ConcurrentHashMap<String, IBinder>()
    }

}