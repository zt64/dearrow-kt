import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    alias(libs.plugins.compatibility) apply false
    alias(libs.plugins.publish) apply false
    alias(libs.plugins.ktlint) apply false
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }

    configure<KtlintExtension> {
        version = rootProject.libs.versions.ktlint
    }

    group = "dev.zt64"
    version = "1.0.0"
}

subprojects {
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}