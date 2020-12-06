pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android") {
                useModule("com.android.tools.build:gradle:7.0.0-alpha02")
            }
        }
    }
}
rootProject.name = "RecipesKMM"


include(":sharedCore")
include(":sharedFrontend")

include(":androidApp")
include(":backend")
include(":desktopConsole")
