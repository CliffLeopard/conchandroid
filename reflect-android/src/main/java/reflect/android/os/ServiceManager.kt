package reflect.android.os

import android.os.IBinder
import com.cliff.reflection.common.annotation.PStaticField
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.os.ServiceManager")
interface ServiceManager {
    @PStaticField
    val sCache: Map<String, IBinder>
}