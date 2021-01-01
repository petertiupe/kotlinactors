plugins {
    kotlin("jvm") version "1.4.21"
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
}

configure<ApplicationPluginConvention> {
    mainClassName = ""
}
