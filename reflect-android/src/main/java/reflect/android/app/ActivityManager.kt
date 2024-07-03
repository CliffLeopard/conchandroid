package reflect.android.app

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.ActivityManager")
interface ActivityManager {
    @PStaticMethod("android.app.IActivityManager")
    fun getService():Any?
}