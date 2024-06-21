package reflect.android.os

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.os.UserHandle")
interface UserHandle {
    @PStaticMethod
    fun myUserId(): Int
}
