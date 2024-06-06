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
        google()
        mavenCentral()
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
include(":nativelib")
include(":common")
include(":nativebinder-service")
project(":common").projectDir = file("ndkbinder/common")
project(":nativebinder-service").projectDir = file("ndkbinder/nativebinder-service")

include(":androidlibrary")
include(":hidden")
include(":compile-part")
