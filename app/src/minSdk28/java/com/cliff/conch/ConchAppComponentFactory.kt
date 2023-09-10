package com.cliff.conch

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Intent
import android.content.pm.ApplicationInfo
import androidx.annotation.RequiresApi
import androidx.core.app.AppComponentFactory
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

@RequiresApi(28)
class ConchAppComponentFactory: AppComponentFactory() {
    companion object {
        init {
            Logger.addLogAdapter(object: AndroidLogAdapter() {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
//                    return BuildConfig.DEBUG
                    return true
                }
            })
        }
    }

    override fun instantiateClassLoader(cl: ClassLoader, aInfo: ApplicationInfo): ClassLoader {
        Logger.d("instantiateClassLoader")
        return super.instantiateClassLoader(cl, aInfo)
    }

    override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
        Logger.d("instantiateApplicationCompat: %s",className)
        return super.instantiateApplicationCompat(cl, className)
    }

    override fun instantiateActivityCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): Activity {
        Logger.d("instantiateActivityCompat: %s", className)
        return super.instantiateActivityCompat(cl, className, intent)
    }

    override fun instantiateReceiverCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): BroadcastReceiver {
        Logger.d("instantiateReceiverCompat: %s",className)
        return super.instantiateReceiverCompat(cl, className, intent)
    }

    override fun instantiateServiceCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): Service {
        Logger.d("instantiateServiceCompat: %s",className)
        return super.instantiateServiceCompat(cl, className, intent)
    }

    override fun instantiateProviderCompat(cl: ClassLoader, className: String): ContentProvider {
        Logger.d("instantiateProviderCompat: %s",className)
        return super.instantiateProviderCompat(cl, className)
    }
}