package com.cliff.conch.reflect

import com.cliff.reflection.common.annotation.PStaticField
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.ActivityTaskManager")
interface PActivityTaskManager {
    @PStaticMethod("android.app.IActivityTaskManager")
    fun getService():Any?

    @PStaticField("android.util.Singleton")
    val IActivityTaskManagerSingleton: Any
}