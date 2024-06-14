package com.cliff.reflection

import com.cliff.reflection.common.annotation.ProxyClass
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

class ReflectSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val options: Map<String, String>,
    private val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(ProxyClass::class.java.name).forEach {
            logger.warn("GGL:" + it.javaClass.name, it)
        }
        return emptyList()
    }
}