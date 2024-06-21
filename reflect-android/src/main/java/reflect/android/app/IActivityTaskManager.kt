package reflect.android.app

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.IActivityTaskManager")
interface IActivityTaskManager {
    @ProxyClass("android.app.IActivityTaskManager\$Stub")
    interface Stub {
        @PStaticMethod("com.cliff.wrapper.service.IActivityTaskManager")
        fun asInterface(obj: android.os.IBinder): Any
    }
}