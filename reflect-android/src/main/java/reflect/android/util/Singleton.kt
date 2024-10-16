package reflect.android.util

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.util.Singleton")
interface Singleton : IReflect {
    companion object

    @PField
    var mInstance: Any?

    @PMethod
    fun get(): Any?
}