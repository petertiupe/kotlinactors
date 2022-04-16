package de.tiupe.hansercoroutines.suspendingfunctions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

suspend fun langDauerndeBerechnung(): Int {
    val asyncResult = GlobalScope.async() {
        var res = 0
        repeat(999_999_999) {
            res = it *10 + 3 / (it + 2)
        }
        res
    }
    return asyncResult.await()
}

fun main(){
    println("Start des Programms")
    runBlocking {
        println("Ergebnis der Berechnung: ${langDauerndeBerechnung()}")
    }
    println("Ende des Programms")
}