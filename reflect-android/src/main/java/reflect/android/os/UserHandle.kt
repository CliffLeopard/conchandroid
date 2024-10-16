package reflect.android.os

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.os.UserHandle")
interface UserHandle : IReflect {
    companion object

    @PStaticMethod
    fun myUserId(): Int
}
