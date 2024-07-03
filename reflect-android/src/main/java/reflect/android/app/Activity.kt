package reflect.android.app

import android.os.IBinder
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.app.Activity")
interface Activity {
    @PMethod
    fun getActivityToken(): IBinder

    // 获取调用起此Activity的uid
    @PMethod
    fun getLaunchedFromUid(): Int

    // 获取调用起此Activity的包名
    @PMethod
    fun getLaunchedFromPackage(): String?
}