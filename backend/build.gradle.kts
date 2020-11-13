plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":sharedCore"))
}

application {
    mainClass.set("ServerKt")
}