import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    // TODO: Phase 2でKSP plugin有効化予定
    // alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.kotlinx.datetime)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${libs.versions.kotlinx.coroutines.get()}")
            // TODO: Phase 2でRoom database有効化予定（Android固有に移動）
            // implementation(libs.room.runtime)
            // implementation(libs.room.ktx)
        }
        androidMain.dependencies {
            // TODO: Phase 2でAndroid specific Room implementation有効化予定
            // implementation(libs.room.runtime)
            // implementation(libs.room.ktx)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    // TODO: Phase 2でRoom compiler KSP configuration有効化予定
    // dependencies {
    //     add("kspAndroid", libs.room.compiler)
    // }
}

android {
    namespace = "com.example.playground.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

// TODO: Phase 2でRoom Database KSP configuration有効化予定
// dependencies {
//     add("kspAndroid", libs.room.compiler)
// }
