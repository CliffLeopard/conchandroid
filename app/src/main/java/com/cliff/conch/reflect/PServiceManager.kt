package com.cliff.conch.reflect

import android.os.IBinder
import com.cliff.reflection.common.annotation.PStaticField
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.os.ServiceManager")
interface PServiceManager {
    @PStaticField
    val sCache: Map<String, IBinder>
}