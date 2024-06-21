package com.cliff.conch.install

import androidx.lifecycle.MutableLiveData

data class AppItem(val name: String, val icon: String) {
    companion object {
        val installedApps: MutableLiveData<MutableList<AppItem>> = MutableLiveData(mutableListOf(
            AppItem("今日头条","kkk")
        ))
        fun installApp(app: AppItem) {
            val newList = installedApps.value ?: mutableListOf()
            newList.add(app)
            installedApps.postValue(newList)
        }

        fun deleteApp(app: AppItem) {
            val newList = installedApps.value ?: mutableListOf()
            newList.remove(app)
            installedApps.postValue(newList)
        }
    }
}