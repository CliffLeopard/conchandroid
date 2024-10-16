import java.io.DataInputStream
import java.net.URI
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("maven-publish")
}

android {
    namespace = "com.cliff.reflect.android"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk21.get().toInt()
        version = libs.versions.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
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
        aidl = true
    }
}

dependencies {
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    ksp(projects.reflectionProcessor)
    implementation(projects.common)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}

val androidJavaDocsProvider = tasks.register("androidJavadocs", Javadoc::class) {
    classpath += project.files(android.bootClasspath)
    android.libraryVariants.filter { variant ->
        variant.name == "release"
    }.forEach { variant ->
        classpath += variant.javaCompileProvider.get().classpath
    }
    exclude("**/R.html", "**/R.*.html", "**/index.html")
}

val androidJavadocsJarProvider = tasks.register("androidJavadocsJar", Jar::class) {
    archiveClassifier.set("javadoc")
    from(androidJavaDocsProvider.get().destinationDir)
}

androidJavadocsJarProvider.get().dependsOn(androidJavaDocsProvider.get())

val androidSourcesJarProvider = tasks.register("androidSourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    if (project.hasProperty("android")) {
        from(android.sourceSets["main"].java.srcDirs)
    } else {
        from(sourceSets["main"].allSource)
    }
}

val localProperties by lazy {
    val localProperties = Properties()
    project.rootProject.file("local.properties")
        .inputStream().use {
            DataInputStream(it).use { inputStream ->
                localProperties.load(inputStream)
            }
        }
    localProperties
}

val isSnapShot = localProperties.getProperty("SNAPSHOT", "false").toBoolean()
fun getRepositoryUrl(): URI {
    return URI.create(localProperties["REPOSITORY_URL"] as? String ?: "")
}

fun getRepositoryUserName():String {
    return localProperties["REPOSITORY_USER_NAME"] as? String ?: ""
}

fun getRepositoryPassword():String {
    return localProperties["REPOSITORY_PASSWORD"] as? String ?: ""
}

afterEvaluate {
    publishing {
        publications {
            register("release",MavenPublication::class) {
                from(components["release"])
                artifacts {
                    androidJavadocsJarProvider.get()
                    androidSourcesJarProvider.get()

                    groupId = project.android.namespace
                    artifactId = project.name
                    version = project.version.toString()
                }
            }
        }

        repositories {
            maven {
                url = getRepositoryUrl()
                credentials {
                    username = getRepositoryUserName()
                    password = getRepositoryPassword()
                }
            }
        }
    }
}