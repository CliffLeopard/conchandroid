package com.cliff.reflection.common.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class PMethodParameter(val value: String)
