package com.cliff.conch.reflect

import java.lang.reflect.Field
import java.lang.reflect.Method

open class BaseProxyImpl(className: String) {
    val clz: Class<*> = Class.forName(className)

    class ObjectFiled<T>(private val field: Field) {
        fun get(obj: Any): T {
            field.isAccessible = true
            return field.get(obj) as T
        }

        fun set(obj: Any, value: T) {
            field.isAccessible = true
            field.set(obj, value)
        }
    }

    class StaticFiled<T>(private val field: Field) {
        fun get(): T {
            field.isAccessible = true
            return field.get(null) as T
        }

        fun set(value: T) {
            field.isAccessible = true
            return field.set(null, value)
        }
    }

    class ObjectMethod<T>(private val method: Method) {
        fun invoke(obj: Any, vararg args: Any?): T {
            method.isAccessible = true
            return method.invoke(obj, args) as T
        }
    }

    class StaticMethod<T>(private val method: Method) {
        fun invoke(varargs: SafeVarargs): T {
            method.isAccessible = true
            return method.invoke(varargs) as T
        }
    }
}