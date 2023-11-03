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
}

rootProject.name = "conch-android"

include(":app")
include(":nativelib")
include(":common")
include(":nativebinder-service")
project(":common").projectDir = file("ndkbinder/common")
project(":nativebinder-service").projectDir = file("ndkbinder/nativebinder-service")

