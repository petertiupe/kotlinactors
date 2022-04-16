package de.tiupe.ktorexample

/*
* Die Dokumentation zu Ktor findet man unter den folgenden URLs
*
* https://ktor.io
* https://github.com/ktorio/ktor
*
* Im Buch wird das Setup via Intellij-Plugin beschrieben, hier gehe ich über eine reine Gradle-Konfiguration.
*
* Folgende Dependencies werden für einen "einfachen" Server benötigt:
*
*       dependencies {
*           implementation "io.ktor:ktor-server-core:1.5.1"
*           implementation "io.ktor:ktor-server-netty:1.5.1"
*           implementation "ch.qos.logback:logback-classic:1.2.3"
*       }
* */

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit {
    //startEmbeddedServer()

    // start via EngineMain
    EngineMain.main(args)

}

fun startEmbeddedServer() {
    embeddedServer(Netty, port = 8000) {
        routing {
            get("/") {
                call.respondText("Hello, world!")
            }
        }
    }.start(wait = true)
}

/* Dieser Server wird über */
fun Application.module(testing: Boolean = false) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}