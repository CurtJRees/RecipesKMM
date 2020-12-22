pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "RecipesKMM"

enableFeaturePreview("GRADLE_METADATA")

include(":sharedCore")
include(":sharedFrontend")

include(":androidApp")
include(":backend")
include(":desktopConsole")