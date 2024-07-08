package reflect.android.app

import android.util.ArrayMap
import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.PStaticField
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass
import java.lang.ref.WeakReference

@ProxyClass("android.app.ActivityThread")
interface ActivityThread {
    @PStaticField("android.content.pm.IPackageManager")
    val sPackageManager: Any?

    @PField("android.app.ActivityThread\$ApplicationThread")
    val mAppThread: Any?

    @PField
    val mPackages:ArrayMap<String,WeakReference<Any>>
    @PField
    val mResourcePackages:ArrayMap<String,WeakReference<Any>>


    @PMethod
    fun getProcessName(): String?

    @PMethod("android.app.ActivityThread\$ApplicationThread")
    fun getApplicationThread():Any

    @PStaticMethod("android.app.ActivityThread")
    fun currentActivityThread(): Any?
}