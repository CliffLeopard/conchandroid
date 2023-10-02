package com.cliff.conch.bean

import com.cliff.conch.scene.EventBusActivity
import com.cliff.conch.scene.ReflectActivity
import com.cliff.conch.scene.RetrofitActivity

data class Section(val title: String, val activity: Class<*>) {
    companion object {
        val sections = mutableListOf(
            Section("EventBus", EventBusActivity::class.java),
            Section("Retrofit", RetrofitActivity::class.java),
            Section("Reflect", ReflectActivity::class.java),
        )
    }
}
