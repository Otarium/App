import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("io.realm.kotlin")
    id("com.codingfeline.buildkonfig")
}

version = "1.0"
val versionCode = 1

kotlin {
    android()
    ios()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("nl.tiebe.magister:api:1.0")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("io.realm.kotlin:library-base:1.0.1")
                implementation("com.russhwolf:multiplatform-settings-no-arg:0.9")
                implementation("io.github.aakira:napier:2.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.work:work-runtime-ktx:2.7.1")
                implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
                implementation("androidx.navigation:navigation-ui-ktx:2.5.1")
            }
        }
        val androidTest by getting
/*        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }*/
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

buildkonfig {
    packageName = "nl.tiebe.openbaarlyceumzeist"

    defaultConfigs {
        buildConfigField(INT, "versionCode", versionCode.toString())
    }
}