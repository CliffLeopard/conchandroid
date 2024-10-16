package reflect.android.app

import android.os.IBinder
import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.IActivityManager")
interface IActivityManager : IReflect {
    companion object

    @PMethod
    fun getLaunchedFromUid(activityToken: IBinder): Int
}