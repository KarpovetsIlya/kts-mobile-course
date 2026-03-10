plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.karpovec.androidapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.karpovec.androidapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["appAuthRedirectScheme"] = "com.karpovec.kmpmobileapp"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "GITHUB_CLIENT_ID", "\"Ov23liezCnsT4mfFrdcf\"")
        buildConfigField("String", "GITHUB_CLIENT_SECRET", "\"e8d2dcc2f95c1f325f47fa141f8bf7701604c590\"")
        buildConfigField("String", "GITHUB_REDIRECT_URI", "\"com.karpovec.kmpmobileapp://oauth/callback\"")

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":composeApp"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.napier)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.appAuth)
    implementation(libs.browser)
}