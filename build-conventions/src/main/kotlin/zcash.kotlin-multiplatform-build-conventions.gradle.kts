import org.gradle.jvm.toolchain.JavaToolchainSpec

pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()?.apply {
        jvmToolchain {
            val javaVersion = JavaVersion.toVersion(project.property("ANDROID_JVM_TARGET").toString())
            val javaLanguageVersion = JavaLanguageVersion.of(javaVersion.majorVersion)
            (this as JavaToolchainSpec).languageVersion.set(javaLanguageVersion)
        }

        targets.all {
            compilations.all {
                kotlinOptions {
                    allWarningsAsErrors = project.property("IS_TREAT_WARNINGS_AS_ERRORS").toString().toBoolean()
                    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
                }
            }
        }

        sourceSets.all {
            // Configure opt-in to various Kotlin APIs
            // languageSettings.optIn("kotlin.time.ExperimentalTime")
        }
    }
}
