plugins {
    kotlin("jvm")
    id(Plugins.detekt)
}

dependencies {
    compileOnly(Deps.detektApi)
    testImplementation(Deps.detektApi)
    testImplementation(Deps.detektTest)
    implementation(Deps.kotlinStdlib)
    implementation(Deps.kotlinxSerialization)
    implementation(Deps.arrowCore)
    implementation(Deps.arrowSyntax)
    testImplementation(Deps.xmlUnitCore)
    testImplementation(Deps.xmlUnitAssertJ)
    testImplementation(Deps.assertJ)
    testImplementation(Deps.kotestArrowAssertions)
    testImplementation(Deps.kotest)
    testImplementation(Deps.kotestAssertions)
    testImplementation(Deps.kotestProperty)
    testImplementation(Deps.xerces)
    testImplementation("org.json:json:20190722")
    testImplementation("com.google.guava:guava:29.0-jre")
    testImplementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.10")
    testImplementation(Deps.jsonAssert)
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")
sourceSets["test"].resources.srcDirs("test-resources")
