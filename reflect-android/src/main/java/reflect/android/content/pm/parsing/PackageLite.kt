package reflect.android.content.pm.parsing

import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.parsing.PackageLite")
interface PackageLite {
    @PMethod
    fun getPackageName():String

    @PMethod
    fun getInstallLocation():Int
}