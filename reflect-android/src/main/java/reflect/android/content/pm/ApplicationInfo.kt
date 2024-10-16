package reflect.android.content.pm

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.ApplicationInfo")
interface ApplicationInfo : IReflect {
    companion object

    @PMethod
    fun isSystemApp():Boolean

    @PMethod
    fun isPrivilegedApp():Boolean
}