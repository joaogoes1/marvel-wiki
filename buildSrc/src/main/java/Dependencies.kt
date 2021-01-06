object App {
    const val kotlinVersion = "1.4.21"
    const val compileSdk = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionCodeName = "1.0.0"
    const val applicationId = "com.joaogoes.marvelwiki"
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.0"
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
        const val fragment = "1.2.5"
    }

    const val core = "androidx.core:core-ktx:${Version.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    const val cardView = "androidx.cardview:cardview:${Version.cardView}"
    const val fragment = "androidx.fragment:fragment:${Version.fragment}"
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

object Glide {
    object Version {
        const val glide = "4.11.0"
    }
    private const val base = "com.github.bumptech.glide"
    const val core = "$base:glide:${Version.glide}"
    const val compiler = "$base:compiler:${Version.glide}"
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
}

object Moshi {
    object Version {
        const val codegen = "1.9.3"
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

    object Espresso {
        object Version {
            const val core = "3.3.0"
        }

        const val core = "androidx.test.espresso:espresso-core:${Version.core}"
    }
}