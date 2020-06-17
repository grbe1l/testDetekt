import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    id("io.gitlab.arturbosch.detekt") version "1.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=io.ktor.locations.KtorExperimentalLocationsAPI"
    sourceCompatibility = "1.8"
    kotlinOptions.jvmTarget = "1.8"
}

the<JavaPluginConvention>().sourceSets.getByName("main").java.srcDirs("kotlin")
