// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies {
        classpath(App.GradlePlugins.android)
        classpath(App.GradlePlugins.kotlin)
        classpath(App.GradlePlugins.safeArgs)
        classpath(App.GradlePlugins.hilt)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}