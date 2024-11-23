enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        // 作为 Xposed 模块使用务必添加，其它情况可选
        maven { url = uri("https://api.xposed.info/") }
        // MavenCentral 有 2 小时缓存，若无法集成最新版本请添加此地址
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
    }

//     //有默认值，再添加就重复了，可以改名字
//    versionCatalogs {
//        create("libs") {
//            from(files("gradle/libs.versions.toml"))
//        }w
//    }
}

rootProject.name = "conch-android"

include(":app")
include(":reflection-processor")
include(":reflection-common")
include(":reflect-android")
include(":hidden")
include(":data")
include(":datastore")
include(":datastore-proto")

include(":nativelib")
//include(":wrapper")
//include(":nativebinder-service")
//include(":common")
//project(":common").projectDir = file("ndkbinder/common")
//project(":nativebinder-service").projectDir = file("ndkbinder/nativebinder-service")
include(":common")
include(":demo")
include(":xposed-module")
