plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${App.kotlinVersion}")
    implementation(project(Modules.charactersPublic))
    implementation(project(Modules.networkPublic))
}