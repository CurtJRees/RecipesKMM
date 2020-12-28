
plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
}
group = "com.curtjrees.recipes"
version = "1.0-SNAPSHOT"

//Workaround for - https://youtrack.jetbrains.com/issue/KT-43944
android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    val sdkName: String? = System.getenv("SDK_NAME")

    jvm()
    android()

    val isiOSDevice = sdkName.orEmpty().startsWith("iphoneos")
    if (isiOSDevice) {
        iosArm64("iOS")
    } else {
        iosX64("iOS")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Coroutines
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}") {
                    isForce = true
                }

                // Kotlinx Serialization
                implementation(Serialization.core)
            }
        }
        val jvmMain by getting
        val androidMain by getting
        sourceSets["iOSMain"].dependencies {}
    }
}
android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        buildConfig = false
    }
}