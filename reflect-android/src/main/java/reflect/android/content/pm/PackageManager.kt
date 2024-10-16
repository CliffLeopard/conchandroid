package reflect.android.content.pm

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.PackageManager")
interface PackageManager : IReflect {
    companion object

    @PMethod
    fun installExistingPackage(packageName:String)
}