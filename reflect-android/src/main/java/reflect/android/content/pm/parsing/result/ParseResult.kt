package reflect.android.content.pm.parsing.result

import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.parsing.result.ParseResult")
interface ParseResult {
    @PMethod
    fun isSuccess():Boolean
    @PMethod
    fun isError():Boolean
    @PMethod
    fun getResult():Any
    @PMethod
    fun getErrorCode():Int
    @PMethod
    fun getErrorMessage():String?
    @PMethod
    fun getException():Exception?
}