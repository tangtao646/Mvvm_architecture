plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tang.base_architecture"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    api("androidx.core:core-ktx:1.9.0")
    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.8.0")
    api("androidx.activity:activity-ktx:1.6.0")
    api("androidx.fragment:fragment-ktx:1.6.0")
    api("com.kingja.loadsir:loadsir:1.3.8")
    api("io.github.scwang90:refresh-layout-kernel:2.0.6")      //核心必须依赖
    api("io.github.scwang90:refresh-header-classics:2.0.6")   //经典刷新头
    api("io.github.scwang90:refresh-footer-classics:2.0.6")    //经典加载
    implementation(project(mapOf("path" to ":commonres")))

}