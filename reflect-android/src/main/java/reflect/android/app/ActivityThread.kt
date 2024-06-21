package reflect.android.app

import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.PStaticField
import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.ActivityThread")
interface ActivityThread {
    @PField("android.app.ActivityThread\$ApplicationThread")
    val mAppThread: Any?

    @PStaticField("android.content.pm.IPackageManager")
    val sPackageManager: Any?

    @PMethod
    fun getProcessName(): String?

    @PMethod("android.app.ActivityThread\$ApplicationThread")
    fun getApplicationThread():Any

    @PStaticMethod
    fun currentActivityThread(): Any?
}