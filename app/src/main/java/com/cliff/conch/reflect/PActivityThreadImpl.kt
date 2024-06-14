package com.cliff.conch.reflect

import com.cliff.reflection.common.MethodType
import com.cliff.reflection.common.annotation.PMethodParameter
import com.cliff.reflection.common.annotation.PMethodType
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.ActivityThread")
interface PActivityThreadImpl {
    // 全部添加上参数注解
    @PMethodType(type = MethodType.FIELD, returnType = "android.app.ActivityThread")
    fun _f_get_mAppThread(obj: Any): Any?

    @PMethodType(type = MethodType.FIELD)
    fun _f_set_AppThread(obj: Any, value: Any?)

    @PMethodType(
        type = MethodType.STATIC_FIELD,
        returnType = "android.content.pm.IPackageManager"
    )
    fun _f_get_sPackageManager(): Any

    @PMethodType(
        type = MethodType.STATIC_FIELD,
        returnType = "android.content.pm.IPackageManager"
    )
    fun _f_set_sPackageManager()

    @PMethodType(type = MethodType.METHOD)
    fun getProcessName(@PMethodParameter("android.app.ActivityThread") obj: Any): String

    @PMethodType(type = MethodType.STATIC_METHOD, returnType = "android.app.ActivityThread")
    fun currentActivityThread(): Any


    @PMethodType(type = MethodType.STATIC_FIELD, returnType = "android.app.ActivityThread")
    fun _f_get_sCurrentActivityThread(): Any

    @PMethodType(type = MethodType.STATIC_FIELD)
    fun _f_set_sCurrentActivityThread(@PMethodParameter("ActivityThread") any: Any?)
}