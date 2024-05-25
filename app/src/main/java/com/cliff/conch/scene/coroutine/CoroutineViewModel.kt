package com.cliff.conch.scene.coroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section

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
            }
        )
    }
}