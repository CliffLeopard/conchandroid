package reflect.android.os

import com.cliff.reflection.common.annotation.PStaticMethod
import com.cliff.reflection.common.annotation.ProxyClass
import java.io.File

@ProxyClass("android.os.Environment")
interface Environment {
    @PStaticMethod
    fun getUserConfigDirectory(userId: Int): File
}