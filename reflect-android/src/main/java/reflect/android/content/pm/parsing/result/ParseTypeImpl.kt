package reflect.android.content.pm.parsing.result

import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.parsing.result.ParseTypeImpl")
interface ParseTypeImpl {
    @PStaticMethod("android.content.pm.parsing.result.ParseTypeImpl")
    fun forDefaultParsing(): Any
    @PMethod("android.content.pm.parsing.result.ParseTypeImpl")
    fun reset():Any
}
