package com.cliff.conch.bean

import android.app.NativeActivity
import com.cliff.conch.scene.BookManagerActivity
import com.cliff.conch.scene.EventBusActivity
import com.cliff.conch.scene.ReflectActivity
import com.cliff.conch.scene.RetrofitActivity
import com.cliff.conch.scene.SelfDefineViewActivity
import com.cliff.nativelib.FoodActivity

data class Section(val title: String, val activity: Class<*>) {
    companion object {
        val sections = mutableListOf(
            Section("EventBus", EventBusActivity::class.java),
            Section("Retrofit", RetrofitActivity::class.java),
            Section("Reflect", ReflectActivity::class.java),
            Section("AIDL", BookManagerActivity::class.java),
            Section("FOOD", FoodActivity::class.java),
            Section("NativeActivity", NativeActivity::class.java),
            Section(
                SelfDefineViewActivity::class.java.simpleName,
                SelfDefineViewActivity::class.java
            ),
        )
    }
}
