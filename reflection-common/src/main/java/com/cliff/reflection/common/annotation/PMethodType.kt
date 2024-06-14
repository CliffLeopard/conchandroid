package com.cliff.reflection.common.annotation

import com.cliff.reflection.common.MethodType

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PMethodType(val type: MethodType, val returnType: String = "")
