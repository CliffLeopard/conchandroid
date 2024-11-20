import org.jetbrains.kotlin.fir.declarations.builder.buildImport

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.cliff.nativelib"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk          = libs.versions.minSdk21.get().toInt()
        ndkVersion      = libs.versions.ndkVersion.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                cppFlags("")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = libs.versions.cmakeVersion.get()
        }
    }

    buildFeatures {
        viewBinding = true
        aidl = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(projects.common)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
//
//tasks.register("compileAidlNdk") {
//    doLast {
//        val aidl = arrayOf(android.sdkDirectory.absolutePath, "build-tools", android.buildToolsVersion, "aidl").joinToString(File.separator)
//        val outDir = arrayOf(projectDir.absolutePath, "src/main/cpp/aidl").joinToString(File.separator)
//        val headerOutDir = arrayOf(projectDir.absolutePath, "src/main/cpp/includes").joinToString(File.separator)
//        val searchPathForImports = arrayOf(projectDir.absolutePath, "src/main/aidl").joinToString(File.separator)
//        val food = arrayOf(projectDir.absolutePath, "src/main/aidl/com/cliff/nativelib/IFood.aidl").joinToString(File.separator)
//        val foodManager = arrayOf(projectDir.absolutePath, "src/main/aidl/com/cliff/nativelib/IFoodManager.aidl").joinToString(File.separator)
//
//        exec {
//            executable(aidl)
//            args(
//                "--lang=ndk",
//                "-o",
//                outDir,
//                "-h",
//                headerOutDir,
//                "-I",
//                searchPathForImports,
//                foodManager
////                food,
////                foodManager
//            )
//        }
//    }
//}
//
//afterEvaluate {
//    tasks.getAt("preBuild").dependsOn(tasks.getAt("compileAidlNdk"))
//}
//
//tasks.getAt("clean").doLast {
//    val aidlCppOutDir =
//        arrayOf(projectDir.absolutePath, "src/main/cpp/aidl").joinToString(File.separator)
//    val aidlCppHeaderOutDir = arrayOf(projectDir.absolutePath, "src/main/cpp/includes/aidl").joinToString(File.separator)
//    File(aidlCppOutDir).deleteRecursively()
//    File(aidlCppHeaderOutDir).deleteRecursively()
//}
