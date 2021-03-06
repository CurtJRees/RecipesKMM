import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "0.2.0-build128"
    application
}

repositories {
    jcenter()
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(project(":sharedCore"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}") // JVM dependency

    implementation(Ktor.clientCore)
    implementation(Ktor.clientCio)
    implementation(Ktor.clientJson)
    implementation(Ktor.clientLogging)
    implementation(Ktor.clientSerialization)

    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.materialIconsExtended)
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.curtjrees.recipes.desktop.MainKt")
}