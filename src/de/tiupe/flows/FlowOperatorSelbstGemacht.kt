package de.tiupe.flows

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*
import kotlin.system.*

fun <T> Flow<T>.uppercase(): Flow<String> {
    return flow {
        collect {
            val value = it.toString().toUpperCase()
            emit(value)
        }
    }
}
val flowC = flowOf("Tina", "Lara", "Inken", "Peter").uppercase()

val flowD = (1..5).asFlow()

// Coroutines sind suspending functions, daher ist der folgende Code möglich.
val flowE: Flow<Int> = flow {
    for (i in 1..30) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking<Unit> {

    val time = measureTimeMillis {
        flowE.collect {
            delay(100)
            println(it)
        }
    }

    println("Collected in $time ms")

    println("Das ganze dauerte $time Sekunden")
}