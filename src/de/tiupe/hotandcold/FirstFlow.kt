package de.tiupe.hotandcold

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.*

@kotlinx.coroutines.InternalCoroutinesApi
fun main(args: Array<String>) = runBlocking {



    /* Hier wird ein erster Flow definiert, der einen Block von Code übergeben
    *  bekommt, der dazu dient, die Zahlen von 1 bis 10 zurückzugeben.
    *  Wie eine Kotlin-Sequence ist dieser Flow kalt und wird nur dann aufgerufen,
    *  wenn er von einem "Consumer" benutzt wird.
    *
    * Wenn man sich die bei einem Flow beteiligten Interfaces ansieht, ist der Flow das Objekt,
    * das die Werte einsammtelt (im Flow-Interface ist die Funktion collect definiert, während der
    * FlowCollector eine Funktion emit hat. Die Namensgebung ist mir noch ein wenig ein Rätsel.
    * */
    val ints: Flow<Int> = flow {
        for (i in 1..10) {
            delay(100)
            emit(i)
        }
    }

    val fibonacciFlow: Flow<Long> =
        (generateSequence( Pair(0L,1L), { p: Pair<Long, Long> ->
            if(p.second < Long.MAX_VALUE / 2) {
                Pair(p.second, (p.first + p.second) )
            } else {
                null
            }
            }).map { it.first }).asFlow().flowOn(Dispatchers.Default)

    Dispatchers.Default

    val fibonacciFlow1: Flow<Long> =
        (generateSequence( Pair(0L,1L), { p: Pair<Long, Long> ->
            if(p.second < Long.MAX_VALUE / 2) {
                Pair(p.second, (p.first + p.second) )
            } else {
                null
            }
        }).map { it.first }).asFlow().flowOn(Dispatchers.IO)




    println("Ausgabe der Fibonacci-Zahlen")
    val jobFibo1 = launch {
        delay(100)
        fibonacciFlow.collect { println("Die nächste Fibonacci-Zahl ist $it") }
    }
    // Hier ein zweiter fibonacci-Job, der dann aber gestoppt wird
    val jobFibo2 = launch {
        delay( 100)
        fibonacciFlow1.collect { println("$it") }
    }

//    jobFibo1.cancel("Habe gent fibonacci-zahlen geseen")

        /*collect ist eine Terminal-Operation. Erst der Aufruf von collect sorgt
        * dafür, dass  der im Flow-Builder flow{} vorhandene Code ausgeführt wird.
        * Der flow-Builder ist lazy genau wie eine Kotlin sequence lazy ist.
        *
        * Zur Bestätigung und zum Vergleich baue ich hier auch noch einmal eine
        * Kotlin Sequence ein.
        * */
        ints.collect {

            println("Die nächste Zahl ist $it")
        }

        val intList = listOf(1,2,3,4,5,6,7,8,9)

        val intSequence: Sequence<Int> = intList.asSequence().map { it * 3 }
        val intFlow: Flow<Int> = intList.asFlow().map { it * 3 }

        // Abrufen der Werte:
        println(intSequence.first { it % 2 == 0 })
        println(intFlow.first{ it % 2 == 0 })

        // man fragt sich sofort, wozu braucht man eigentlich beide?
        // Der Unterschied besteht darin, dass ein Kotlin Flow auch "suspending Functions"
        // in seinen Operatoren aufrufen kann, was eine Sequence nicht kann.
        // Außerdem kann man bei den Flows  Mechanismen nutzen, die die Kotlin Coroutines API
        // bietet, bei den Sequnces geht das nicht.

        // Hier der Aufruf eines Custom-Flow-Operators
        println("Custom-Flow-Operator")
        println(listOf(1,2,3,4,5).asFlow().plusOne().first())

}



fun <T> Flow<T>.plusOne(): Flow<Int> {
    return flow<Int> {
        collect {
            val value1: Int = it as Int
            val value: Int = value1 + 1
            emit(value)
        }
    }
}