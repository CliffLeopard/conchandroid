package reflect.android.content.pm

import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.ApplicationInfo")
interface ApplicationInfo {
    @PMethod
    fun isSystemApp():Boolean

    @PMethod
    fun isPrivilegedApp():Boolean
}