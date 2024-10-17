package com.cliff.demo

import android.app.Application
import com.cliff.common.HiddenExecutor
import com.cliff.reflection.common.Reflect

/**
 * @Author CliffLeopard
 * @Email  precipiceleopard@gmail.com
 * @Time   2024/10/16 18:08
 */
class DApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Reflect.registerExecutor(HiddenExecutor())
    }
}