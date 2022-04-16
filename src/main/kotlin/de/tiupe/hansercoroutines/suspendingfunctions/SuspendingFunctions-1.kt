package de.tiupe.hansercoroutines.suspendingfunctions

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    runBlocking {
        launch {
            val a = 5
            val b = 7
            val sum = doSomeCalculation(a, b) { x, y ->
                x + y
            }

            val diff = doSomeCalculation(a, b) { x, y ->
                y - x
            }

            val bothTogether = doSomeCalculation(diff, sum) { x, y ->
                x + y
            }

            println("Summe: $sum, Differenz: $diff und Summe aus beiden ist: $bothTogether")

        }

        val summe = async {
            val a = 5
            val b = 7
            println("async Sum")
            doSomeCalculation(a, b) { x, y ->
                x + y
            }
        }

        val diff = async {
            val a = 5
            val b = 7
            println("async Diff")
            doSomeCalculation(a, b) { x, y ->
                y - x
            }
        }

        val bothTogether = doSomeCalculation(summe.await(), diff.await()){x, y ->
            x + y
        }
        println("Summe ${summe.getCompleted()}, Diff ${diff.getCompleted()}, bothTogether: $bothTogether")
    }
}

suspend fun doSomeCalculation(a: Int, b: Int, function: (Int, Int) -> Int): Int {
    println("Trying to calculate")
    delay(2000)
    println("going to deliver result")
    return function(a, b)
}