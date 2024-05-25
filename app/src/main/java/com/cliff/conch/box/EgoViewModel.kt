package com.cliff.conch.box

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section
import com.cliff.conch.box.reflect.RefMethod

class EgoViewModel : ViewModel() {
    private val _sections = MutableLiveData(egos)
    val sections: LiveData<List<Section>> get() = _sections

    companion object {
        val egos: List<Section> = listOf(
            Section("Mirror映射实现", RefMethod::class.java) {
                MirrorCases.testMirror()
            },
        )
    }
}