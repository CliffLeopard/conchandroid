package com.cliff.conch.bean

import android.app.NativeActivity
import com.cliff.conch.scene.EventBusActivity
import com.cliff.conch.scene.PineActivity
import com.cliff.conch.scene.ReflectActivity
import com.cliff.conch.scene.RetrofitActivity
import com.cliff.conch.scene.SelfDefineViewActivity
import com.cliff.nativelib.FoodActivity

data class Section(val title: String, val activity: Class<*>) {
    companion object {
        private val activities = listOf(
            EventBusActivity::class.java,
            RetrofitActivity::class.java,
            ReflectActivity::class.java,
            FoodActivity::class.java,
            NativeActivity::class.java,
            SelfDefineViewActivity::class.java,
            PineActivity::class.java

        )
        val sections: List<Section> = activities.map {
            Section(it.simpleName, it)
        }
    }
}
