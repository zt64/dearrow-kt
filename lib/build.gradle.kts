import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.compatibility)
    alias(libs.plugins.publish)
}

description = "Ktor wrapper for DeArrow API"

kotlin {
    explicitApi()
    jvmToolchain(17)

    jvm()

    js {
        browser()
        nodejs()
    }

    // Enable once https://youtrack.jetbrains.com/issue/KTOR-5587/Ktor-client-for-Kotlin-Wasm stable
    // wasmJs {
    //     browser()
    //     nodejs()
    // }

    native()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.hash)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.coroutines.test)
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        linuxMain {
            dependencies {
                implementation(libs.ktor.client.curl)
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    coordinates("$group", "dearrow-kt", "${project.version}")
    signAllPublications()

    val path = "github.com/zt64/dearrow-kt"

    pom {
        description = project.description
        inceptionYear = "2024"
        url = "https://$path"

        developers {
            developer {
                id = "zt64"
                name = "zt64"
                email = "31907977+zt64@users.noreply.github.com"
            }
        }

        licenses {
            license {
                name = "GNU General Public License v3.0"
                url = "https://www.gnu.org/licenses/gpl-3.0.html"
            }
        }

        scm {
            url = "https://$path"
            connection = "scm:git:git://$path.git"
            developerConnection = "scm:git:ssh://git@$path.git"
        }
    }
}

private fun KotlinMultiplatformExtension.native(configure: KotlinNativeTarget.() -> Unit = {}) {
    val hostOs = System.getProperty("os.name").lowercase()
    val isAmd64 = System.getProperty("os.arch") == "amd64"

    when {
        hostOs.startsWith("mac") -> {
            if (isAmd64) {
                listOf(
                    macosX64(),
                    iosX64(),
                    tvosX64(),
                    watchosX64()
                )
            } else {
                listOf(
                    macosArm64(),
                    iosArm64(),
                    iosSimulatorArm64(),
                    tvosArm64(),
                    tvosSimulatorArm64(),
                    watchosArm64(),
                    watchosSimulatorArm64()
                )
            }
        }

        hostOs.startsWith("linux") -> {
            listOf(if (isAmd64) linuxX64() else linuxArm64())
        }

        hostOs.startsWith("windows") -> listOf(mingwX64())

        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }.forEach(configure)
}