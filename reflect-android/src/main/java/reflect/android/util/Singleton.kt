package reflect.android.util

import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.util.Singleton")
interface Singleton {
    @PField
    val mInstance: Any?

    @PMethod
    fun get(): Any?
}