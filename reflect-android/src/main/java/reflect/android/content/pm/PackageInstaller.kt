package reflect.android.content.pm

import com.cliff.reflection.common.IReflect
import com.cliff.reflection.common.annotation.PField
import com.cliff.reflection.common.annotation.PMethod
import com.cliff.reflection.common.annotation.ProxyClass

@ProxyClass("android.content.pm.PackageInstaller")
interface PackageInstaller : IReflect {
    companion object

    @PMethod
    fun setPermissionsResult(sessionId: Int, accepted: Boolean)

    @ProxyClass("android.content.pm.PackageInstaller\$Session")
    interface Session : IReflect {
        companion object

        @PMethod
        fun addProgress(progress: Float)
    }

    @ProxyClass("android.content.pm.PackageInstaller\$SessionParams")
    interface SessionParams : IReflect {
        companion object

        @PMethod
        fun setInstallAsInstantApp(isInstantApp: Boolean)

        @PField
        val abiOverride: String
    }

    @ProxyClass("android.content.pm.PackageInstaller\$SessionInfo")
    interface SessionInfo : IReflect {
        companion object

        @PField
        var sealed: Boolean

        @PField
        var resolvedBaseCodePath: String?
    }

}
