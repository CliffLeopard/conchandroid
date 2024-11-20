import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

// 读取签名
val propertyFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(propertyFile))

android {
    namespace = "com.cliff.conch"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId   = "com.cliff.conch"
        minSdk          = libs.versions.minSdk21.get().toInt()
        ndkVersion      = libs.versions.ndkVersion.get()
        targetSdk       = libs.versions.targetSdk.get().toInt()
        versionCode     = libs.versions.versionCode.get().toInt()
        versionName     = libs.versions.versionName.get()
        resourceConfigurations.addAll(listOf("cn", "en")) // 语言配置
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        externalNativeBuild {
//            cmake {
//                cppFlags("")
//            }
//        }
    }
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = libs.versions.cmakeVersion.get()
        }
    }

    signingConfigs {
        getByName("debug") {
            keyAlias        = keystoreProperties["keyAlias"] as String
            keyPassword     = keystoreProperties["keyPassword"] as String
            storeFile       = rootProject.file(keystoreProperties["storeFile"] as String)
            storePassword   = keystoreProperties["storePassword"] as String

            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }

        register("release") {
            keyAlias        = keystoreProperties["keyAlias"] as String
            keyPassword     = keystoreProperties["keyPassword"] as String
            storeFile       = rootProject.file(keystoreProperties["storeFile"] as String)
            storePassword   = keystoreProperties["storePassword"] as String

            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        aidl = true
    }

    flavorDimensions.add("minSdk")
    productFlavors {
        register("minSdk28") {
            dimension = "minSdk"
            minSdk = libs.versions.minSdk28.get().toInt()
        }
        register("minSdk21") {
            dimension = "minSdk"
            minSdk = libs.versions.minSdk21.get().toInt()
        }
    }
    sourceSets {
        getByName("main") {
            aidl.srcDirs("src/main/aidl")
        }
    }
}

dependencies {
    ksp(projects.reflectionProcessor)
    kapt(libs.eventbus.annotation)
    kapt(libs.hilt.android.compiler)

    implementation(projects.reflectAndroid)
    implementation(projects.nativelib)
    implementation(projects.data)
    implementation(projects.common)

    implementation(libs.pine.core)
    implementation(libs.pine.enhances)
    implementation(libs.pine.xposed)

    implementation(libs.gson)
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.startup)
    implementation(libs.eventbus)

    implementation(libs.hilt.android)
    implementation(libs.annotation)
    implementation(libs.slice.builders)
    implementation(libs.androidx.activity)
    implementation(libs.logger)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.vectordrawable)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}

kapt {
    arguments {
        arg("eventBusIndex", "com.cliff.eventbuskotlin.MyEventBusIndex")
    }
    correctErrorTypes = true
}