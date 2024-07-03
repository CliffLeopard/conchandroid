package reflect.android.content.pm

import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.PackageManager")
interface PackageManager {
    @PMethod
    fun installExistingPackage(packageName:String)
}