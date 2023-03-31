plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
    id("org.jetbrains.dokka")
    id("app.cash.paparazzi") version libs.versions.paparazzi.get()
}

group "com.togitech"
version "2.1.0"

kotlin {
    jvmToolchain(11)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.togitech.ccp"

    defaultConfig {
        minSdk = 24
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    publishing {
        singleVariant("release") {
            withJavadocJar()
        }
    }
}

dependencies {
    api(libs.kotlinx.immutable)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.compose)
//    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

    implementation(libs.androidx.core)
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.libphonenumber)
    implementation(libs.compose.tooling.preview)
    debugImplementation(libs.compose.tooling)

    detektPlugins("ru.kode:detekt-rules-compose:1.2.2")
    detektPlugins("com.twitter.compose.rules:detekt:0.0.26")
}

detekt {
    config = files(project.rootProject.file("detekt.yml"))
    allRules = true
}

afterEvaluate {
    publishing {
        publications {
            create("release", MavenPublication::class.java) {
                from(components.getByName("release"))
                groupId = "com.togisoft"
                artifactId = "jetpack_country_code_picker"
                version = "2.1.0"
            }
        }
    }
}