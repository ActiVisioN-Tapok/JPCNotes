plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("plugin.serialization") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.jpcnotes"
    compileSdk = 34

    val key: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty("supabaseKey")

    val url: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty("supabaseUrl")

    defaultConfig {
        applicationId = "com.example.jpcnotes"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "supabaseKey", "\"$key\"")
        buildConfigField("String", "supabaseUrl", "\"$url\"")
    }

    buildTypes {
        release {
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.2"))
    implementation("io.github.jan-tennert.supabase:auth-kt:3.0.2")
    implementation("androidx.navigation:navigation-compose:2.8.6")


    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    implementation ("io.supabase:supabase-kt:3.1.1")

    implementation("io.github.jan-tennert.supabase:storage-kt")
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.ktor:ktor-client-android:3.0.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}