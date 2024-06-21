package com.cliff.conch.reflect

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.os.UserHandle")
interface PUserHandler {
    @PStaticMethod
    fun myUserId(): Int

}