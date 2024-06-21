package com.cliff.conch.box

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section
import com.cliff.conch.box.dp.DynamicProxyCase
import com.cliff.conch.box.service.SystemServerInterceptor
import com.cliff.conch.install.InstallPackageActivity
import com.orhanobut.logger.Logger
import reflect.android.app.ActivityThreadReImpl
import reflect.android.os.EnvironmentReImpl
import reflect.android.os.ServiceManagerReImpl
import reflect.android.os.UserHandleReImpl

class EgoViewModel : ViewModel() {
    private val _sections = MutableLiveData(egos)
    val sections: LiveData<List<Section>> get() = _sections

    companion object {
        val egos: List<Section> = listOf(
            Section("隐藏代码Mirror映射实现") {
                MirrorCases.testMirror()
            },
            Section("动态代理实现") {
                DynamicProxyCase.testDynamicProxy()
            },
            Section("查看系统都注册了哪些服务") {
                val map = ServiceManagerReImpl.sCache_s_get_()
                for (section in map) {
                    Logger.i("服务: name:${section.key}  value:${section.value.javaClass.name}")
                }

                Logger.i("服务总数: ${map.size}")
            },
            Section("创建ProxyServer，代理系统SystemServer") {
                SystemServerInterceptor.interceptATMS()
            },
            Section("安装应用", InstallPackageActivity::class.java),
            Section("反射调用系统API") {
                Logger.i("GGL:反射调用系统API")
                val activityThread = ActivityThreadReImpl.currentActivityThread()!!
                val processName = ActivityThreadReImpl.getProcessName(activityThread)
                Logger.i("GGL:$processName")
            },
            Section("获取当前用户文件配置") {
                val userId = UserHandleReImpl.myUserId()
                val env = EnvironmentReImpl.getUserConfigDirectory(userId)
                Logger.i("GGL:userId:$userId envConfig: ${env.absolutePath}")
            }
        )
    }
}