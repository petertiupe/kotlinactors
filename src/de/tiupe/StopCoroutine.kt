package de.tiupe
import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        println("Tiefschlaf ...")
        delay(60_000)
        println("ausgeschlafen")
    }

    delay(10_000)
    println("Jetzt reicht's!")
    job.cancelAndJoin() // Der Befehl bricht ab und wartet auf die
                        // richtige Beendigung der Coroutine.
    println("Ende.")
}