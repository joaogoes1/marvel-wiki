import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
val publicKey: String = gradleLocalProperties(rootDir).getProperty("PUBLIC_KEY")
val privateKey: String = gradleLocalProperties(rootDir).getProperty("PRIVATE_KEY")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = App.compileSdk
    buildToolsVersion = App.buildToolsVersion

    defaultConfig {
        minSdk = App.minSdk
        targetSdk = App.targetSdk
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
        pickFirst("**/META-INF/MANIFEST.MF")
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
    implementation(AndroidX.swipeToRefresh)
    implementation(OkHttp.core)
    implementation(Glide.core)
    implementation(Hilt.hilt)
    implementation(Material.material)
    implementation(Moshi.codegen)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.moshiConverter)
    implementation(Room.runtime)
    implementation(Room.ktx)
    implementation(ViewBinding.viewBinding)

    //remove this
    implementation(project(Modules.networkPublic))

    kapt(Glide.compiler)
    kapt(Room.compiler)
    kapt(Hilt.compiler)

    testImplementation(Test.AndroidX.coreTesting)
    testImplementation(Test.JUnit.jUnit)
    testImplementation(Test.Kotlin.coroutines)
    testImplementation(Test.Mockk.mockk)
    testImplementation(Test.OkHttpTest.mockWebServer)
    testImplementation(Test.Room.room)
}