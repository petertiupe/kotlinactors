package de.tiupe.asynchnachbuch

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val job1 = async {
        delay(10_000)
        10
    }

    val job2 = async {
        delay(5_000)
        5
    }

    // Ergebnisse verarbeiten

    val result = job2.await() + job1.await()



    println("Ergebnis des Jobs ist: $result")

    println("Hier gehts erst weiter, wenn die Ergebnisse vorliegen ...")
    println("und tschüss")
}