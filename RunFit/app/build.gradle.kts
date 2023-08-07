plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    kotlin("android")
}

android {
    namespace = "com.example.runfit"
    compileSdk = 34

    buildFeatures{
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.runfit"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    val roomversion = "2.5.2"
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Material Design
    implementation ("com.google.android.material:material:1.9.0")
    //Architectural Component
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    //Room Database
    implementation ("androidx.room:room-ktx:$roomversion")
    implementation ("androidx.room:room-runtime:$roomversion")
    kapt("androidx.room:room-compiler:2.5.2")
    androidTestImplementation ("androidx.room:room-testing:$roomversion")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Coroutines Lifecycle Scope
    val archversion = "2.6.1"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$archversion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$archversion")

    //Navigation Component
    val navversion = "2.6.0"
    implementation("androidx.navigation:navigation-fragment-ktx:$navversion")
    implementation("androidx.navigation:navigation-ui-ktx:$navversion")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    //Google Maps Location Services
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    //Dagger Core
    implementation("com.google.dagger:dagger:2.44")
    kapt("com.google.dagger:dagger-compiler:2.44")

    //Dagger Android
    api ("com.google.dagger:dagger-android:2.28.1")
    api ("com.google.dagger:dagger-android-support:2.28.1")
    kapt ("com.google.dagger:dagger-android-processor:2.23.2")

    //Activity KTX for View Models
    implementation("androidx.activity:activity-ktx:1.7.2")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    kapt("com.google.dagger:hilt-android-compiler:2.44")

    // Easy Permission
    implementation ("pub.devrel:easypermissions:3.0.0")

    // Timber
    implementation ("com.jakewharton.timber:timber:4.7.1")

    //Mp Android Chart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation ("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")
}

