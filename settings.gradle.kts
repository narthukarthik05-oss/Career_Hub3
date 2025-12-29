// settings.gradle.kts
pluginManagement {
    repositories {
        google()              // Required for Android Gradle plugin
        mavenCentral()        // Required for dependencies
        gradlePluginPortal()  // Required for Kotlin plugin
    }

    plugins {
        id("com.android.application") version "8.1.0" apply false
        id("org.jetbrains.kotlin.android") version "1.9.10" apply false
        id("com.google.gms.google-services") version "4.3.15" apply false
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CareerHub3"
include(":app")
