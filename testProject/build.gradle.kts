import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

// Top-level build file where you add configuration options common to all sub-modules
// NOTE: Do not place your application dependencies here; they belong in the individual
// build.gradle.kts files
plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin apply false
    kotlin("kapt") version Versions.kotlin apply false
    id(Plugins.allopen) version Versions.kotlin apply false
    id(Plugins.noarg) version Versions.kotlin apply false
    id(Plugins.shadow) version Versions.shadow apply false
    id(Plugins.detekt) version Versions.detekt
}

dependencies {
    // These two dependencies enforce KTLint rules through detekt :)
    detekt(Deps.detektFormatting)
    detekt(Deps.detektCli)
}

tasks.withType(io.gitlab.arturbosch.detekt.Detekt::class).configureEach() {
    dependsOn(":libraries:custom-detekt:assemble")
}

val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Runs over whole code base without the starting overhead for each module."
    parallel = true
    buildUponDefaultConfig = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektFormat by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Reformats whole code base."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(files("$rootDir/config/detekt/format.yml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

tasks.check {
    dependsOn(detektAll)
}

allprojects {
    group = "com.testProject.detekt"
    version = "1.0-0"
    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    repositories {
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-dev/") }
        maven { url = uri("http://maven.icm.edu.pl/artifactory/repo/") }
        maven { url = uri("https://repo.spring.io/plugins-release/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
    tasks.withType<KotlinCompile> {
        sourceCompatibility = "1.8"
        kotlinOptions {
            jvmTarget = "1.8"
            allWarningsAsErrors = true
            freeCompilerArgs += listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xuse-experimental=kotlin.Experimental"
            )
        }
    }
}

val schemaName by lazy { schemaName() }

configurations.all {
    exclude("org.slf4j", "slf4j-log4j12")
}

fun schemaName(): String {
    val workingBranch = getWorkingBranch()
    if (workingBranch == "master") {
        return ""
    }
    val branchName = if (workingBranch.length - 1 > 30) {
        workingBranch.substring(0, 30)
    } else {
        workingBranch
    }
    val schemaName = "CI_${branchName
        .replace('/', '_')
        .replace('-', '_')
        .toUpperCase()}"
    val schemaNameShort = schemaName.take(29)
    println("Schema name $schemaName (first 29 chars: $schemaNameShort)")
    return schemaNameShort
}

fun getWorkingBranch(): String {
    val jenkinsBranch = System.getenv("GIT_BRANCH")
    if (jenkinsBranch != null && jenkinsBranch.isNotEmpty()) {
        return jenkinsBranch
    }
    val command = "git rev-parse --abbrev-ref HEAD"
    return runCommand(command)
}

fun runCommand(command: String): String {
    val output = ByteArrayOutputStream()
    project.exec {
        commandLine = command.split(" ")
        standardOutput = output
    }
    return output.toString()
}
