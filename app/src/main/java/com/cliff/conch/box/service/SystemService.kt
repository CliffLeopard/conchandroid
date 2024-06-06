package com.cliff.conch.box.service

abstract class SystemService {
    abstract val className: String
    abstract val serverName: String
    abstract val methods: List<MethodNode>

    data class MethodNode(
        val name: String,
        val parameterTypes: List<Class<*>>,
        val returnType: Class<*>
    )
}