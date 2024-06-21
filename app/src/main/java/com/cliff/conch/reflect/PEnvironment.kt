package com.cliff.conch.reflect

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass
import java.io.File
@ProxyClass("android.os.Environment")
interface PEnvironment {
    @PStaticMethod
    fun getUserConfigDirectory(userId: Int):File
}