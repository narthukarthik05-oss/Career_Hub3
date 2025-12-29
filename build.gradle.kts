// root build.gradle.kts
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("com.google.gms:google-services:4.3.15")
        classpath(kotlin("gradle-plugin", version = "1.9.10"))
    }
}

// If you want, you can apply plugins imperatively here in root, but normally root doesn't need them.
