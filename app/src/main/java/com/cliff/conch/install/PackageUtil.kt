package com.cliff.conch.install

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import java.io.File
import kotlin.math.max

object PackageUtil {
    private const val PREFIX: String = "com.android.packageinstaller."
    const val INTENT_ATTR_INSTALL_STATUS: String = PREFIX + "installStatus"
    const val INTENT_ATTR_APPLICATION_INFO: String = PREFIX + "applicationInfo"
    const val INTENT_ATTR_PERMISSIONS_LIST: String = PREFIX + "PermissionsList"
    const val INTENT_ATTR_PACKAGE_NAME: String = PREFIX + "PackageName"


    fun getPackageInfo(context: Context, sourceFile: File, flags: Int): PackageInfo? {
        return try {
            context.packageManager.getPackageArchiveInfo(sourceFile.absolutePath, flags)
        } catch (ignored: Exception) {
            null
        }
    }

    /**
     * Get the maximum target sdk for a UID.
     *
     * @param context The context to use
     * @param uid The UID requesting the install/uninstall
     *
     * @return The maximum target SDK or -1 if the uid does not match any packages.
     */
    fun getMaxTargetSdkVersionForUid(context: Context, uid: Int): Int {
        val pm = context.packageManager
        val packages = pm.getPackagesForUid(uid)
        var targetSdkVersion = -1
        if (packages != null) {
            for (packageName in packages) {
                try {
                    val info = pm.getApplicationInfo(packageName!!, 0)
                    targetSdkVersion =
                        max(targetSdkVersion.toDouble(), info.targetSdkVersion.toDouble()).toInt()
                } catch (e: PackageManager.NameNotFoundException) {
                    // Ignore and try the next package
                }
            }
        }
        return targetSdkVersion
    }
}