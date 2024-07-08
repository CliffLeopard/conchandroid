package reflect.com.android.internal.content

import com.cliff.reflection.common.annotation.PMethodParameter
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("reflect.com.android.internal.content.InstallLocationUtils")
interface InstallLocationUtils {
    @PStaticMethod
    fun calculateInstalledSize(@PMethodParameter("android.content.pm.parsing.PackageLite") pkg: Any, abiOverride: String): Long
}