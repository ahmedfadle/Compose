plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("com.google.devtools.ksp")
    id ("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
//    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.compose.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.compose.app"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.runtime.livedata )
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)


    //Retrofit
    implementation(libs.square.retrofit)
    implementation(libs.square.adapter.rxjava2)
    implementation(libs.square.converter.moshi)
    implementation (libs.jsoup)
    implementation (libs.converter.gson)
    implementation (libs.converter.simplexml)

    //OkHttpClient
    implementation(libs.square.okhttp.logging.interceptor)

    //hilt
    implementation (libs.hilt.android.v248)
    ksp( libs.dagger.compiler) // Dagger compiler
    ksp (libs.hilt.compiler.v248) // Hilt compiler
    implementation("androidx.compose.material:material:1.8.0-rc03")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")



}