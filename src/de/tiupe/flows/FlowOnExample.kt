package de.tiupe.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

val flowA = flow {
    println("Emitting coroutine -> $coroutineContext")
    emit("Tina")
    emit("Inken")
    emit("Lara")
    delay(100)
    emit("Done")
}

// Der hier erzeugte Flow flowB läuft in der Umgebung von
// dem Dispatcher.IO-Dispatcher. So hat man also auch die Möglichkeit
// die Ablaufumgebung für den Flow zu steuern.
val flowB = flowA.flowOn(Dispatchers.IO)

fun main() = runBlocking {
    flowB.collect {
        println("Collecting coroutine -> $coroutineContext and value $it")
    }
}