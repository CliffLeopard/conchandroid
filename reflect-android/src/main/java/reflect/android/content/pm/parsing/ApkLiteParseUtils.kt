package reflect.android.content.pm.parsing

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PMethodParameter
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass
import java.io.File

@ProxyClass("android.content.pm.parsing.ApkLiteParseUtils")
interface ApkLiteParseUtils : IReflect {
    companion object

    @PStaticMethod("android.content.pm.parsing.result.ParseResult")
    fun parsePackageLite(
        @PMethodParameter("android.content.pm.parsing.result.ParseInput") input: Any,
        packageFile: File,
        flags: Int): Any

}