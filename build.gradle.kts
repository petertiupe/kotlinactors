

plugins {
    application
    kotlin("jvm") version "1.6.20"

    // needed for the serialization-example
    kotlin("plugin.serialization") version "1.6.10"



}

val retrofit_version = "2.9.0"


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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    // https://mvnrepository.com/artifact/org.openjfx/javafx
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("io.ktor:ktor-server-core:1.5.1")
    implementation("io.ktor:ktor-server-netty:1.5.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:retrofit-mock:$retrofit_version")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")


    implementation( "io.reactivex.rxjava2:rxjava:2.2.21")
    implementation( "io.reactivex.rxjava2:rxkotlin:2.4.0")

    implementation( "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.4.2")

}

configure<ApplicationPluginConvention> {
    mainClassName = ""
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}