package reflect.android.view

import android.content.res.Configuration
import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.ProxyClass
import reflect.android.content.res.CompatibilityInfo

@ProxyClass("android.view.DisplayAdjustments")
interface DisplayAdjustments : IReflect {
    companion object

    @PField("android.content.res.CompatibilityInfo")
    val mCompatInfo: CompatibilityInfo

    @PField
    val mConfiguration: Configuration
}