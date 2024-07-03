package reflect.android.app

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.AppGlobals")
interface AppGlobals {
    @PStaticMethod("android.content.pm.IPackageManager")
    fun getPackageManager(): Any?
}