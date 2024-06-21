package com.cliff.conch.scene.reflect

import android.os.IBinder
import com.cliff.reflection.common.annotation.PConstructor
import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.PMethodParameter
import com.cliff.reflection.common.annotation.PStaticField
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("com.cliff.conch.scene.reflect.ReflectCase")
interface PReflectCase {
    @PField
    val name: String?

    @PField
    val jp: JavaParameter?

    @PField
    val sCache: Map<String, IBinder>?

    @PField
    val rt: ReflectCaseReturn?

    @PField("com.cliff.conch.scene.reflect.ReflectCaseReturn")
    val rt2: Any?

    @PStaticField
    val logo: String?

    @PStaticField("com.cliff.conch.scene.reflect.ReflectCaseReturn")
    val srt: Any?

    @PMethod
    fun getRT(name: String, age: Int, rp: ReflectCasePara): ReflectCaseReturn

    @PMethod("com.cliff.conch.scene.reflect.ReflectCaseReturn")
    fun getRT2(
        name: String,
        age: Int,
        @PMethodParameter("com.cliff.conch.scene.reflect.ReflectCasePara") rp: Any
    ): Any?


    @PStaticMethod
    fun getRTS(name: String, age: Int, rp: ReflectCasePara): ReflectCaseReturn

    @PStaticMethod("com.cliff.conch.scene.reflect.ReflectCaseReturn")
    fun getRTS2(
        name: String,
        age: Int,
        @PMethodParameter("com.cliff.conch.scene.reflect.ReflectCasePara") rp: Any
    ): Any?

    @PConstructor("com.cliff.conch.scene.reflect.ReflectCase")
    fun getInstance(name: String, age: Int): Any?

    @PConstructor
    fun getInstance2(name: String, age: Int): ReflectCase

    @PConstructor
    fun getInstance3(name: String, age: Int): ReflectCase

}