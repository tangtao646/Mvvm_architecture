plugins {
    id("com.android.library")
}

android {
    namespace = "com.tang.commonres"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

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

    dependencies {
        implementation("androidx.appcompat:appcompat:1.3.0")
        implementation("com.google.android.material:material:1.4.0")
    }

}

