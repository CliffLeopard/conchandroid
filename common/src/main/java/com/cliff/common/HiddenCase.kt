package com.cliff.common

import com.cliff.hidden.HiddenApi

/**
 * @Author CliffLeopard
 * @Email  precipiceleopard@gmail.com
 * @Time   2024/10/17 12:30
 */
object HiddenCase {
    fun hiddenInfo(): String {
        val clz = Class.forName("android.view.DisplayAdjustments")
        return HiddenApi.getInstanceFields(clz).joinToString { it.name }
    }
}