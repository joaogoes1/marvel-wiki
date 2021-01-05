object App {
    const val kotlinVersion = "1.4.21"
    const val compileSdk = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionCodeName = "1.0.0"
    const val applicationId = "com.joaogoes.marvelwiki"
    const val gradleBuildTools = "com.android.tools.build:gradle:4.1.1"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
}

object Kotlin {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${App.kotlinVersion}"
}

object AndroidX {
    object Version {
        const val core = "1.3.2"
        const val appCompat = "1.2.0"
        const val constraintLayout = "2.0.4"
    }

    private const val androidx = "androidx"
    const val core = "$androidx.core:core-ktx:${Version.core}"
    const val appCompat = "$androidx.appcompat:appcompat:${Version.appCompat}"
    const val constraintLayout =
        "$androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
}

object MaterialComponents {
    object Version {
        const val material = "1.2.1"
    }

    const val material = "com.google.android.material:material:${Version.material}"
}

object Dagger{
    object Version {
        const val dagger = "2.30.1"
    }
    const val dagger = "com.google.dagger:dagger:${Version.dagger}"
    const val androidProcessor = "com.google.dagger:dagger-android-processor:${Version.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Version.dagger}"
}

object Test {
    object JUnit {
        object Version {
            const val jUnit = "4.13.1"
            const val testExt = "1.1.2"
        }

        const val jUnit = "junit:junit:${Version.jUnit}"
        const val testExt = "androidx.test.ext:junit:${Version.testExt}"
    }

    object Espresso {
        object Version {
            const val core = "3.3.0"
        }

        const val core = "androidx.test.espresso:espresso-core:${Version.core}"
    }
}