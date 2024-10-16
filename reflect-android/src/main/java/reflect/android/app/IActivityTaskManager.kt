package reflect.android.app

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.IActivityTaskManager")
interface IActivityTaskManager : IReflect {
    companion object

    @ProxyClass("android.app.IActivityTaskManager\$Stub")
    interface Stub : IReflect {
        companion object

        @PStaticMethod("com.cliff.wrapper.service.IActivityTaskManager")
        fun asInterface(obj: android.os.IBinder): Any
    }
}