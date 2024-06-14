import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isIncludeCompileClasspath
import proguard.gradle.ProGuardTask

//import org.jetbrains.kotlin.fir.declarations.builder.buildImport

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

buildscript {
    dependencies {
        classpath(libs.proguard.gradle)
    }
}

task("proguardTask",ProGuardTask::class) {
    description = "Obfuscates source files"
    val artifactName = "reflection-common.jar" // 根据实际情况修改！！！
    val inputFolder = "$buildDir/libs"
    val obfuscatedFolder = "$buildDir/obfuscated"
    val inputJar = "$inputFolder/$artifactName"
    val outputJar = "$obfuscatedFolder/$artifactName"

    injars(inputJar)
    outjars(outputJar)
    printseeds("$obfuscatedFolder/seeds.txt")
    printmapping("$obfuscatedFolder/mapping.txt")
    libraryjars("${System.getProperty("java.home")}/lib/rt.jar")
    libraryjars(configurations.runtimeElements)
    libraryjars(sourceSets.main)
    configuration(files("proguard-rules.pro"))
    dontshrink()
    delete(obfuscatedFolder)
    doLast {
        delete(inputJar)
        copy {
            from(outputJar)
            into(inputFolder)
        }
    }
}

