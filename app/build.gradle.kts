import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val publicKey: String = gradleLocalProperties(rootDir).getProperty("PUBLIC_KEY")
val privateKey: String = gradleLocalProperties(rootDir).getProperty("PRIVATE_KEY")

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
            buildConfigField("String", "PUBLIC_KEY", publicKey)
            buildConfigField("String", "PRIVATE_KEY", privateKey)
        }
        getByName("debug") {
            buildConfigField("String", "PUBLIC_KEY", publicKey)
            buildConfigField("String", "PRIVATE_KEY", privateKey)
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
        dataBinding = true
        viewBinding = true
    }
    packagingOptions {
        pickFirst("META-INF/*")
    }
}
dependencies {
    implementation(Kotlin.kotlinStdLib)
    implementation(Kotlin.Coroutines.android)
    implementation(AndroidX.core)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.cardView)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.fragment)
    implementation(OkHttp.core)
    implementation(Glide.core)
    implementation(OkHttp.loggingInterceptor)
    implementation(Dagger.android)
    implementation(Dagger.androidSupport)
    implementation(Jetpack.Navigation.featureModule)
    implementation(Jetpack.Navigation.fragment)
    implementation(Jetpack.Navigation.ui)
    implementation(Moshi.codegen)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.moshiConverter)
    implementation(Room.runtime)
    implementation(Room.ktx)
    implementation(ViewBinding.noReflection)

    kapt(Dagger.androidProcessor)
    kapt(Dagger.compiler)
    kapt(Glide.compiler)
    kapt(Room.compiler)

    testImplementation(Test.AndroidX.coreTesting)
    testImplementation(Test.JUnit.jUnit)
    testImplementation(Test.Kotlin.coroutines)
    testImplementation(Test.Mockk.mockk)
    testImplementation(Test.OkHttpTest.mockWebServer)
    testImplementation(Test.Room.room)

    androidTestImplementation(Test.Espresso.core)
    androidTestImplementation(Test.JUnit.testExt)
}