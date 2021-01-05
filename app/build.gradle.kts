plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
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
    implementation(AndroidX.cardView)
    implementation(AndroidX.constraintLayout)
    implementation(Dagger.android)
    implementation(Dagger.androidSupport)
    implementation(Jetpack.Navigation.featureModule)
    implementation(Jetpack.Navigation.fragment)
    implementation(Jetpack.Navigation.ui)
    implementation(Retrofit.retrofit)
    implementation(ViewBinding.noReflection)

    kapt(Dagger.androidProcessor)
    kapt(Dagger.compiler)

    testImplementation(Test.JUnit.jUnit)
    androidTestImplementation(Test.JUnit.testExt)
    androidTestImplementation(Test.Espresso.core)
}