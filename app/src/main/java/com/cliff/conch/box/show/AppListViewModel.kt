package com.cliff.conch.box.show

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author CliffLeopard
 * @Email  precipiceleopard@gmail.com
 * @Time   2024/10/29 15:25
 */
class AppListViewModel : ViewModel() {
    private var _apps = MutableLiveData<List<ApplicationInfo>>(listOf())
    val apps: LiveData<List<ApplicationInfo>> get() = _apps
    fun loadApps(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            context.packageManager.getInstalledApplications(0).let {
                _apps.postValue(it)
            }
        }
    }
}