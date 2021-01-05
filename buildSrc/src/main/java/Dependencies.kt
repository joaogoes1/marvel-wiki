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
    const val safeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Jetpack.Navigation.Version.navigation}"
}

object AndroidX {
    object Version {
        const val core = "1.3.2"
        const val appCompat = "1.2.0"
        const val constraintLayout = "2.0.4"
        const val cardView = "1.0.0"
    }

    const val core = "androidx.core:core-ktx:${Version.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    const val cardView = "androidx.cardview:cardview:${Version.cardView}"
}

object Dagger {
    object Version {
        const val dagger = "2.30.1"
    }

    private const val dagger = "com.google.dagger"
    const val android = "$dagger:dagger-android:${Version.dagger}"
    const val androidSupport = "$dagger:dagger-android-support:${Version.dagger}"
    const val androidProcessor = "$dagger:dagger-android-processor:${Version.dagger}"
    const val compiler = "$dagger:dagger-compiler:${Version.dagger}"
}

object Jetpack {
    object Navigation {
        object Version {
            const val navigation = "2.3.2"
        }

        const val fragment = "androidx.navigation:navigation-fragment-ktx:$${Version.navigation}"
        const val ui = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
        const val featureModule =
            "androidx.navigation:navigation-dynamic-features-fragment:${Version.navigation}"
    }
}

object Kotlin {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${App.kotlinVersion}"
}

object Moshi {
    object Version {
        const val codegen = "1.11.0"
    }
    const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.codegen}"
}

object Retrofit {
    object Version {
        const val retrofit = "2.9.0"
    }

    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
}

object ViewBinding {
    object Version {
        const val viewBinding = "1.4.0"
    }

    private const val viewBinding = "com.kirich1409.viewbindingpropertydelegate"
    const val noReflection = "$viewBinding:vbpd-noreflection:${Version.viewBinding}"
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

    object NavigationComponent {
        const val test =
            "androidx.navigation:navigation-testing:${Jetpack.Navigation.Version.navigation}"
    }

    object Espresso {
        object Version {
            const val core = "3.3.0"
        }

        const val core = "androidx.test.espresso:espresso-core:${Version.core}"
    }
}