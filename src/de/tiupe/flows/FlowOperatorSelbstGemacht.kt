package de.tiupe.flows

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*
import kotlin.system.*

/**
 * Eine Extension-Function auf dem Flow, sodass man später auf dem Flow
 * einfach uppercase aufrufen kann. In der Funktion wird wieder von
 * dem Flow-Producer gebrauch gemacht. Es wird ein Flow von Strings erzeugt.
 * */
fun <T> Flow<T>.uppercase(): Flow<String> {
    return flow {
        collect {
            val value = it.toString().toUpperCase()
            emit(value)
        }
    }
}
/**
 * Im Folgenden werden drei Varianten gezeigt, wie man einen Flow erzeugen
 * kann, wobei die Erzeugung von flowA die obige Extension-Function nutzt.
 *
 * Die drei Möglichkeiten sind:
 *      flowOf
 *      asFlow
 *      flow{}
 * */
private val flowA = flowOf("Tina", "Lara", "Inken", "Peter").uppercase()

private val flowB = (1..5).asFlow()

// Coroutines sind suspending functions, daher ist der folgende Code möglich.
private val flowC: Flow<Int> = flow {
    for (i in 1..30) {
        delay(100)
        emit(i)
    }
}

/**
 * Die Main-Function macht nichts anderes, als die oben erzeugten Flows zu nutzen und
 * deren Ergebnisse zurückzugeben.
 * */
fun main() = runBlocking<Unit> {
    val timeA = measureTimeMillis {
        flowA.collect {
            delay(100)
            println(it)
        }
    }

    val timeB = measureTimeMillis {
        flowB.collect {
            delay(100)
            println(it)
        }
    }

    val timeC = measureTimeMillis {
        flowC.collect {
            delay(100)
            println(it)
        }
    }

    println("Collect for FlowA:  $timeA ms")
    println("Collect for FlowB:  $timeB ms")
    println("Collect for FlowC:  $timeC ms")
}