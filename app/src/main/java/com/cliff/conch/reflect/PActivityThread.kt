package com.cliff.conch.reflect

import com.cliff.reflection.common.annotation.OriginClass
import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.PStaticField
import com.cliff.reflection.common.annotation.PStaticMethod

@OriginClass("android.app.ActivityThread")
interface PActivityThread {
    @PField("android.app.ActivityThread")
    val mAppThread: Any?

    @PStaticField("android.content.pm.IPackageManager")
    val sPackageManager: Any

    @PMethod
    fun getProcessName(name :String, age:Int, tmp:Any?): String

    @PStaticMethod
    fun currentActivityThread(): Any
}


//static volatile IPackageManager sPackageManager