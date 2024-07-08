package com.cliff.reflection.common

import com.cliff.reflection.common.hidden.HiddenApi
import java.lang.ref.WeakReference
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

open class ProxyBaseImpl protected constructor(originCls: String) {
    private val clz: Class<*> by lazy {
        Class.forName(originCls)
    }
    private val fields = HashMap<String, WeakReference<Field>>()
    private val methods = HashMap<String, WeakReference<Method>>()
    private val constructors = HashMap<String, WeakReference<Constructor<*>>>()

    companion object {
        private val instanceMap = HashMap<String, WeakReference<ProxyBaseImpl>>()
        fun getInstance(originCls: String): ProxyBaseImpl {
            if (!instanceMap.contains(originCls) || instanceMap[originCls]!!.get() == null) {
                instanceMap[originCls] = WeakReference(ProxyBaseImpl(originCls))
            }
            return instanceMap[originCls]?.get()!!
        }
    }

    fun setFiled(
        target: Any?,
        fieldName: String,
        `value`: Any?,
        type: String,
        isStatic: Boolean = false
    ) {
        val typeName = extractType(type)
        if (`value` != null
            && !typeName.contains("kotlin")
            && !Class.forName(typeName).isInstance(`value`)
        ) {
            throw ReflectException("File value type error")
        }
        try {
            findField(fieldName).set(target, `value`)
        } catch (exp: NoSuchFieldException) {
            findHiddenField(fieldName, isStatic)?.set(target, `value`)
        }
    }

    fun <T> getFiled(target: Any?, fieldName: String, type: String, isStatic: Boolean = false): T? {
        val `value` = try {
            findField(fieldName).get(target)
        } catch (exp: NoSuchFieldException) {
            findHiddenField(fieldName, isStatic)?.get(target)
        }
        val typeName = extractType(type)
        return if (`value` == null)
            null
        else if (typeName.contains("kotlin") || Class.forName(extractType(type)).isInstance(`value`))
            value as T
        else
            throw ReflectException("File return type error")

    }

    fun <T> invokeMethod(target: Any?, methodName: String, vararg sections: Section): T {
        val parameters = sections.map { it.data }.toTypedArray()
        val parameterTypes = sections.map { it.type }.toTypedArray()
        val methodKey = methodKey(methodName, *parameterTypes)
        return try {
            println("invokeMethod:$methodName")
            if (methods[methodKey] == null || methods[methodKey]?.get() == null) {
                val method = clz.getDeclaredMethod(methodName, *parameterTypes)
                method.isAccessible = true
                val result = method.invoke(target, *parameters)
                methods[methodKey] = WeakReference(method)
                result as T
            } else {
                methods[methodKey]!!.get()!!.invoke(target, *parameters) as T
            }

        } catch (exp: NoSuchMethodException) {
            println(exp.localizedMessage)
            println("invokeMethod2 hidden:$methodName")
            HiddenApi.invoke(clz, target, methodName, *parameters) as T
        }
    }

    fun <T> invokeConstructor(classType: String, vararg sections: Section): T {
        val clsType = Class.forName(classType)
        val parameters = sections.map { it.data }.toTypedArray()
        val parameterTypes = sections.map { it.type }.toTypedArray()
        val key = methodKey("constructor", *parameterTypes)
        return try {
            if (constructors[key] == null || constructors[key]?.get() == null) {
                val constructor = clsType.getDeclaredConstructor(*parameterTypes)
                constructor.isAccessible = true
                val value = constructor.newInstance(*parameters)
                constructors[key] = WeakReference<Constructor<*>>(constructor)
                value as T
            } else {
                constructors[key]!!.get()!!.newInstance(*parameters) as T
            }
        } catch (exp: NoSuchMethodException) {
            HiddenApi.newInstance(clsType, *parameters) as T
        }
    }

    private fun findField(fieldName: String): Field {
        if (fields[fieldName] == null || fields[fieldName]?.get() == null) {
            val filed = clz.getDeclaredField(fieldName)
            filed.isAccessible = true
            fields[fieldName] = WeakReference(filed)
        }
        return fields[fieldName]!!.get()!!
    }

    private fun findHiddenField(fieldName: String, isStatic: Boolean = false): Field? {
        val field = if (isStatic) {
            HiddenApi.getStaticFiled(fieldName, clz)
        } else {
            HiddenApi.getInstanceFiled(fieldName, clz)
        }
        if (field != null) {
            field.isAccessible = true
            fields[fieldName] = WeakReference(field)
        }
        return field
    }

    private fun methodKey(methodName: String, vararg parameterTypes: Class<*>): String {
        return "$methodName;" + parameterTypes.joinToString {
            it.canonicalName + ";"
        }
    }
}