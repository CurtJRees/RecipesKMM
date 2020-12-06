plugins {
    kotlin("jvm")
    application
    kotlin("plugin.serialization")
}

repositories {
    maven("https://dl.bintray.com/kotlin/exposed")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}") // JVM dependency

    implementation("io.ktor:ktor-server-core:${Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${Versions.ktor}")
    implementation("io.ktor:ktor-serialization:${Versions.ktor}")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("org.jetbrains.exposed:exposed:0.17.7")
    implementation("org.postgresql:postgresql:42.2.18")

    implementation(project(":sharedCore"))
}

application {
    mainClass.set("com.curtjrees.recipes.server.ServerKt")
}

tasks {
    val stage by creating {
        dependsOn(installDist)
    }
}
