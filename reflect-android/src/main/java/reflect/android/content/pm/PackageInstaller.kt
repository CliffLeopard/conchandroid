package reflect.android.content.pm

import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.PackageInstaller")
interface PackageInstaller {
    @ProxyClass("android.content.pm.PackageInstaller\$SessionParams")
    interface SessionParams {
        @PMethod
        fun setInstallAsInstantApp(isInstantApp:Boolean)
    }
}