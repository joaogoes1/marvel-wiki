plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(App.compileSdk)
    buildToolsVersion(App.buildToolsVersion)

    defaultConfig {
        applicationId = App.applicationId
        minSdkVersion(App.minSdk)
        targetSdkVersion(App.targetSdk)
        versionCode = App.versionCode
        versionName = App.versionCodeName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    android {
        buildFeatures {
            dataBinding = true
            viewBinding = true
        }
    }
}

dependencies {
    implementation(Kotlin.kotlinStdLib)
    implementation(AndroidX.core)
    implementation(AndroidX.appCompat)
    implementation(MaterialComponents.material)
    implementation(AndroidX.constraintLayout)
    implementation(Dagger.dagger)

    kapt(Dagger.androidProcessor)
    kapt(Dagger.compiler)

    testImplementation(Test.JUnit.jUnit)
    androidTestImplementation(Test.JUnit.testExt)
    androidTestImplementation(Test.Espresso.core)
}