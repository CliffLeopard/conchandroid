package com.cliff.conch.scene.coroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.cliff.conch.bean.Section
import com.cliff.conch.scene.coroutine.imitate.lua.testLuaCoroutine
import com.cliff.conch.scene.coroutine.imitate.lua.testLuaSymCoroutine
import com.cliff.conch.scene.coroutine.imitate.testAsyncWait
import com.cliff.conch.scene.coroutine.imitate.testGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoroutineViewModel : ViewModel() {
    private val _sections = MutableLiveData(coroutines)
    val sections: LiveData<List<Section>> get() = _sections
    companion object {
        val coroutines: List<Section> = listOf(
            Section("协程基础-createCoroutine") {
                CoroutineCase.basicCreate()
            },
            Section("协程基础-startCoroutine") {
                CoroutineCase.basicStart()
            },
            Section("协程基础-扩展函数startCoroutine") {
                CoroutineCase.basicReceiverCreate()
            },
            Section("协程基础-仿PythonGenerator") {
                testGenerator()
            },
            Section("协程基础-仿JS AsyncWait") {
                it.findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.IO) {
                    testAsyncWait()
                }
            },
            Section("协程基础-仿Lua Coroutine") {
                it.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    testLuaCoroutine()
                }
            },
            Section("协程基础-仿Lua SysCoroutine") {
                it.findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.IO) {
                    testLuaSymCoroutine()
                }
            }
        )
    }
}