package reflect.com.android.server.pm.parsing

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass
import reflect.com.android.server.pm.parsing.pkg.ParsedPackage
import java.io.File

@ProxyClass("com.android.server.pm.parsing.PackageParser2")
interface PackageParser2 : IReflect {
    companion object

    @PStaticMethod("com.android.server.pm.parsing.PackageParser2")
    fun forParsingFileWithDefaults():Any

    @PMethod("com.android.server.pm.parsing.pkg.ParsedPackage")
    fun parsePackage(packageFile: File, flags:Int,  useCaches:Boolean):ParsedPackage
}