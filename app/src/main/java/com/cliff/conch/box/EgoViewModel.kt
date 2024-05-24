package com.cliff.conch.box

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section
import com.cliff.conch.box.reflect.RefMethod
import com.cliff.conch.box.scene.ActivityThread
import com.cliff.conch.scene.SimpleCasesActivity
import com.orhanobut.logger.Logger

class EgoViewModel : ViewModel() {
    private val _sections = MutableLiveData(egos)
    val sections: LiveData<List<Section>> get() = _sections

    companion object {
        val egos: List<Section> = listOf(
            Section(SimpleCasesActivity::class.java.simpleName, SimpleCasesActivity::class.java),
            Section("RefMethod", RefMethod::class.java) {
                Logger.d(if (ActivityThread.currentActivityThread == null) "是NULL" else "不是NULL:")
                val mainThread = ActivityThread.currentActivityThread.call()
                val processName = ActivityThread.getProcessName.call(mainThread)
                Logger.d(processName)
            },
            Section("InvokeStaticMethod") {
                ReflectCases.invokeStaticMethod()
            },
            Section("InvokeStaticField") {
                ReflectCases.invokeStaticField()
            },
            Section("InvokeMethod") {
                ReflectCases.invokeMethod()
            },
            Section("InvokeField") {
                ReflectCases.invokeField()
            }
        )
    }
}