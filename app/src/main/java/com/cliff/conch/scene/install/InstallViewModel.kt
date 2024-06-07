package com.cliff.conch.scene.install

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.conch.ConchApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class InstallViewModel : ViewModel() {
    val apps: LiveData<MutableList<AppItem>> get() = AppItem.installedApps
    fun install() {
        viewModelScope.launch {
            installApk()
        }
    }

    private suspend fun installApk(){
        withContext(Dispatchers.IO) {
            ConchApplication.context.assets.open("Now.apk")
        }
    }
}