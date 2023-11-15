import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.afisha"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.afisha"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_KEY", gradleLocalProperties(rootDir).getProperty("apiKey"))
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding=true
        buildConfig=true
    }
}

dependencies {
    val hiltVersion = "2.48.1"
    val lifecycle = "2.6.2"
    val activity = "1.8.0"
    val fragment = "1.6.2"
    val appcompat = "1.6.1"
    val material = "1.10.0"
    val constraintlayout = "2.1.4"
    val retrofit = "2.9.0"
    val picasso = "2.71828"
    val junit = "4.13.2"
    val ktx = "1.12.0"
    val recyclerView = "1.3.2"
    val appcompatAlpha = "1.7.0-alpha03"
    val materialBeta = "1.11.0-beta01"
    val roomVersion = "2.6.0"
    val cicerone = "7.1"
    val materialDesign = "1.11.0-beta01"
    val lottie = "5.2.0"
    val pagingLibrary = "3.2.1"

    implementation("androidx.core:core-ktx:${ktx}")
    implementation("androidx.appcompat:appcompat:${appcompat}")
    implementation("androidx.appcompat:appcompat:${appcompatAlpha}")
    implementation("com.google.android.material:material:${material}")
    implementation("com.google.android.material:material:${materialBeta}")
    implementation("androidx.constraintlayout:constraintlayout:${constraintlayout}")

    // Fragment/ViewModel
    implementation("androidx.fragment:fragment-ktx:${fragment}")
    implementation("androidx.activity:activity-ktx:${activity}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycle}")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:${recyclerView}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${hiltVersion}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofit}")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")

    // Picasso
    implementation("com.squareup.picasso:picasso:${picasso}")

    // Cicerone
    implementation("com.github.terrakok:cicerone:${cicerone}")

    // Material Design
    implementation("com.google.android.material:material:${materialDesign}")

    // Lottie
    implementation("com.airbnb.android:lottie:${lottie}")

    // Paging Library
    implementation("androidx.paging:paging-runtime-ktx:${pagingLibrary}")

    // JUnit
    testImplementation("junit:junit:${junit}")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}