package com.cliff.reflection.common.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ProxyClass(val value:String)
