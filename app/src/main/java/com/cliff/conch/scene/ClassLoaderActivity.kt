package com.cliff.conch.scene

import android.app.Application
import android.os.Bundle
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.ConchApplication
import com.cliff.conch.databinding.ActivityClassLoaderBinding
import com.orhanobut.logger.Logger

class ClassLoaderActivity : BaseActivity<ActivityClassLoaderBinding>() {
    private val activityThreadName = "android.app.ActivityThread"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val atLoader = Class.forName(activityThreadName).classLoader
        val applicationLoader = Application::class.java.classLoader
        val conchLoader = ConchApplication::class.java.classLoader
        Logger.i(
            "activityThread:   ${atLoader}\n" +
                    "application:       $applicationLoader\n" +
                    "conch:             $conchLoader \n" +
                    "systemClassLoader: ${ClassLoader.getSystemClassLoader()}"
        )

        Logger.i(
            "application:      $conchLoader\n" +
                    "ap's parent:       ${conchLoader?.parent} \n" +
                    "a's  parent:       ${conchLoader?.parent?.parent}"
        )
        if (conchLoader == ClassLoader.getSystemClassLoader()) {
            Logger.i("conch is system class loader")
        } else {
            Logger.i("conch is not system class loader")
        }
    }

    override fun initBinding() {
        binding = ActivityClassLoaderBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }
}