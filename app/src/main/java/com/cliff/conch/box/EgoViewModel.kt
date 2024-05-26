package com.cliff.conch.box

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section
import com.cliff.conch.box.dp.DynamicProxyCase
import com.cliff.conch.box.service.FakeActivityTaskManager
import com.orhanobut.logger.Logger

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
            Section("虚拟SystemService实现-替换ActivityTaskManager和ActivityTaskManagerService") {
                Logger.i("获取ActivityTaskMangerService")
                FakeActivityTaskManager.getService().startNextMatchingActivity(null, null, null)
            }
        )
    }
}