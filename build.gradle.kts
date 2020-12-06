buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("xml-apis:xml-apis:1.4.01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}")
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
    }
}
group = "com.curtjrees.recipes"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}