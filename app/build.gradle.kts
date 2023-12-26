plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tang.base"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tang.base"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        dataBinding = true
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
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


}

dependencies {
    implementation(project(mapOf("path" to ":commonres")))
    implementation(project(mapOf("path" to ":base-mvvm")))
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:2.19.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0-RC")
    testImplementation("app.cash.turbine:turbine:0.10.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}