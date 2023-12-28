package com.cliff.conch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section

class HomeViewModel : ViewModel() {
    private val _sections = MutableLiveData(
        Section.sections
    )
    val sections: LiveData<List<Section>> get() = _sections
}