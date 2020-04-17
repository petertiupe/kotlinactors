package de.tiupe.coroutinesnachbuch

import kotlinx.coroutines.*


/*
*
* The main difference between these two is that the runBlocking method blocks
* the current thread for waiting, while coroutineScope just suspends,
* releasing the underlying thread for other usages.
*
* * */

// runBlocking blockiert den Thread, während coroutineScope
// den Thread wieder für die weitere Verwendung freigibt.
fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope { // Creates a coroutine scope
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // This line will be printed before the nested launch
    }

    println("Coroutine scope is over") // This line is not printed until the nested launch completes
}