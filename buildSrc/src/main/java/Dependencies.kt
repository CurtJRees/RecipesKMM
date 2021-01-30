
object Versions {
    const val gradle = "7.0.0-alpha05"

    const val kotlin = "1.4.21"
    const val kotlinCoroutines = "1.4.2-native-mt"
    const val kotlinCoroutinesAndroid = "1.4.2"
    const val kotlinxSerialization = "1.0.1"

    const val ktor = "1.5.0"
    const val sqlDelight = "1.4.2"

    const val koin = "3.0.0-alpha-4"

    const val compose = "1.0.0-alpha10"
    const val nav_compose = "1.0.0-alpha04"
    const val accompanist = "0.4.1"

    const val kermit = "0.1.8"

    const val junit = "4.13"
    const val testRunner = "1.3.0"
}


object AndroidSdk {
    const val min = 21
    const val compile = 30
    const val target = compile
}

object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
    const val accompanist= "dev.chrisbanes.accompanist:accompanist-coil:${Versions.accompanist}"
}

object Koin {
    val core = "org.koin:koin-core:${Versions.koin}"
    val android = "org.koin:koin-android:${Versions.koin}"
    val androidViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
}

object Ktor {
    val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
    val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"

    val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    val clientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
    val clientCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    val clientJs = "io.ktor:ktor-client-js:${Versions.ktor}"
}

object Serialization {
    val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
}

object SqlDelight {
    val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
    val coroutineExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
    val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"

    val nativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
}

