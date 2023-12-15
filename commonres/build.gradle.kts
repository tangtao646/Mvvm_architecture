plugins {
    id("com.android.library")
}

android {
    namespace = "com.tang.commonres"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
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
  

}

