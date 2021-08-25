object App {
    const val kotlinVersion = "1.5.21"
    const val compileSdk = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionCodeName = "1.0.0"
    const val applicationId = "com.joaogoes.marvelwiki"

    object GradlePlugins {
        const val android = "com.android.tools.build:gradle:7.0.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Jetpack.Navigation.Version.navigation}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.Version.hilt}"
    }
}

object AndroidX {
    object Version {
        const val core = "1.3.2"
        const val appCompat = "1.2.0"
        const val constraintLayout = "2.0.4"
        const val cardView = "1.0.0"
        const val fragment = "1.2.5"
        const val swipeToRefresh = "1.1.0"
    }

    const val core = "androidx.core:core-ktx:${Version.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    const val cardView = "androidx.cardview:cardview:${Version.cardView}"
    const val fragment = "androidx.fragment:fragment:${Version.fragment}"
    const val swipeToRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeToRefresh}"
}

object Glide {
    object Version {
        const val glide = "4.11.0"
    }

    private const val base = "com.github.bumptech.glide"
    const val core = "$base:glide:${Version.glide}"
    const val compiler = "$base:compiler:${Version.glide}"
}

object Hilt {
    object Version {
        const val hilt = "2.38.1"
    }

    const val hilt = "com.google.dagger:hilt-android:${Version.hilt}"
    const val compiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
    // For instrumentation tests
    const val instrumentationTest = "com.google.dagger:hilt-android-testing:${Version.hilt}"
    const val instrumentationTestCompiler = "com.google.dagger:hilt-compiler:${Version.hilt}"
    // For local unit tests
    const val unitTest = "com.google.dagger:hilt-android-testing:${Version.hilt}"
    const val unitTestCompiler = "com.google.dagger:hilt-compiler:${Version.hilt}"
}

object Jetpack {
    object Navigation {
        object Version {
            const val navigation = "2.3.2"
        }

        private const val base = "androidx.navigation"
        const val fragment = "$base:navigation-fragment-ktx:$${Version.navigation}"
        const val ui = "$base:navigation-ui-ktx:${Version.navigation}"
        const val featureModule = "$base:navigation-dynamic-features-fragment:${Version.navigation}"
    }
}

object Kotlin {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${App.kotlinVersion}"

    object Coroutines {
        object Version {
            const val android = "1.3.9"
        }
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.android}"
    }
}

object Material {
    object Version {
        const val material = "1.2.1"
    }
    const val material = "com.google.android.material:material:${Version.material}"
}

object Moshi {
    object Version {
        const val codegen = "1.12.0"
    }

    const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.codegen}"
}

object OkHttp {
    object Version {
        const val okhttp = "4.9.0"
    }

    private const val base = "com.squareup.okhttp3"
    const val core = "$base:okhttp:${Version.okhttp}"
    const val loggingInterceptor = "$base:logging-interceptor:4.9.0"
}

object Retrofit {
    object Version {
        const val retrofit = "2.9.0"
    }

    private const val base = "com.squareup.retrofit2"
    const val retrofit = "$base:retrofit:${Version.retrofit}"
    const val moshiConverter = "$base:converter-moshi:${Version.retrofit}"
}

object Room {
    object Version {
        const val room = "2.4.0-alpha04"
    }
    const val runtime = "androidx.room:room-runtime:${Version.room}"
    const val compiler = "androidx.room:room-compiler:${Version.room}"
    const val ktx = "androidx.room:room-ktx:${Version.room}"
}

object ViewBinding {
    object Version {
        const val viewBinding = "1.4.6"
    }

    const val viewBinding = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Version.viewBinding}"
}

object Test {
    object AndroidX {
        object Version {
            const val coreTesting = "2.1.0"
        }
        const val coreTesting = "androidx.arch.core:core-testing:${Version.coreTesting}"
    }

    object Espresso {
        object Version {
            const val core = "3.3.0"
        }

        const val core = "androidx.test.espresso:espresso-core:${Version.core}"
    }

    object JUnit {
        object Version {
            const val jUnit = "4.13.1"
            const val testExt = "1.1.2"
        }

        const val jUnit = "junit:junit:${Version.jUnit}"
        const val testExt = "androidx.test.ext:junit:${Version.testExt}"
    }

    object Kotlin {
        object Version {
            const val coroutines = "1.4.2"
        }
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
    }

    object OkHttpTest {
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${OkHttp.Version.okhttp}"
    }

    object Mockk {
        object Version {
            const val mockk = "1.10.4"
        }

        const val mockk = "io.mockk:mockk:${Version.mockk}"
    }

    object NavigationComponent {
        const val test =
            "androidx.navigation:navigation-testing:${Jetpack.Navigation.Version.navigation}"
    }

    object Room {
        const val room = "androidx.room:room-testing:2.2.5"
    }
}