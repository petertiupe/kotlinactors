plugins {
    kotlin("jvm") version "1.4.30"

    // needed for the serialization-example
    kotlin("plugin.serialization") version "1.4.30"

}



group = "de.tiupe"
version = "1.0-SNAPSHOT"

apply {
    plugin("kotlin")
    plugin("application")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-js
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

}

configure<ApplicationPluginConvention> {
    mainClassName = ""
}
