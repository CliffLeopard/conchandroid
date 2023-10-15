import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
}

// 读取签名
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.cliff.conch"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId   = "com.cliff.conch"
        minSdk          = libs.versions.minSdk16.get().toInt()
        targetSdk       = libs.versions.targetSdk.get().toInt()
        versionCode     = libs.versions.versionCode.get().toInt()
        versionName     = libs.versions.versionName.get()
        resourceConfigurations.addAll(listOf("cn", "en")) // 语言配置
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }

        getByName("release") {
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
        register("minSdk16") {
            dimension = "minSdk"
            minSdk = libs.versions.minSdk16.get().toInt()
        }
    }
    sourceSets {
        getByName("main") {
            aidl.srcDirs("src/main/aidl")
        }
    }
}

dependencies {
    implementation(project(":nativelib"))
    implementation(libs.eventbus)
    implementation(libs.annotation)
    implementation(libs.slice.builders)
    kapt(libs.eventbus.annotation)
    implementation(libs.logger)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.vectordrawable)
    implementation(libs.androidx.livedata)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}

kapt {
    arguments {
        arg("eventBusIndex", "com.cliff.eventbuskotlin.MyEventBusIndex")
    }
}