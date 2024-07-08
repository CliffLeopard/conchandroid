package reflect.com.android.server.pm.parsing

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass
import java.io.File

@ProxyClass("com.android.server.pm.parsing.PackageParser2")
interface PackageParser2 {
    @PStaticMethod("com.android.server.pm.parsing.PackageParser2")
    fun forParsingFileWithDefaults():Any

    fun parsePackage(packageFile: File, flags:Int,  useCaches:Boolean)
}