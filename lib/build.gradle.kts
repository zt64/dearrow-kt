import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.*

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

    linuxX64()
    linuxArm64()

    mingwX64()

    apple()

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

private fun KotlinMultiplatformExtension.apple(configure: KotlinNativeTarget.() -> Unit = {}) {
    val isMacOs = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("mac")

    if (!isMacOs) return

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64(),
        tvosX64(),
        tvosArm64(),
        tvosSimulatorArm64(),
        watchosArm32(),
        watchosArm64(),
        watchosSimulatorArm64()
    ).forEach(configure)
}