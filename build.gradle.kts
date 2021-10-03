plugins {
    application
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

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("com.example.ApplicationKt")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-js
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    // https://mvnrepository.com/artifact/org.openjfx/javafx
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("io.ktor:ktor-server-core:1.5.1")
    implementation("io.ktor:ktor-server-netty:1.5.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")


}

configure<ApplicationPluginConvention> {
    mainClassName = ""
}
