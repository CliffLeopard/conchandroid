package com.cliff.conch.tools

import com.orhanobut.logger.Logger

/**
 * @Author CliffLeopard
 * @Email  precipiceleopard@gmail.com
 * @Time   2024/10/28 09:04
 */
object DefaultKtCase {
    fun println() {
        val a = KtA()
        a.hello()
        val z = object :KtAInterface {}
        z.hello()
    }

    interface KtAInterface {
        fun hello() {
            Logger.i("KtAInterface hello")
        }
    }

    class KtA : KtAInterface {
        override fun hello() {
            Logger.i("KtA hello")
        }
    }
}