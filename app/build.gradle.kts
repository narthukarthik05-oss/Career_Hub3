plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") // <-- Important: Google Services Plugin
}

android {
    namespace = "com.example.careerhub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.careerhub"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.google.firebase:firebase-analytics:21.3.0")
    // Firebase BoM (Manages versions automatically)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Firebase products
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // Core AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // UI
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // RecyclerView (important for CGPA list)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
