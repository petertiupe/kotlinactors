package de.tiupe.hansercoroutines.suspendingfunction

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun someCalculation(): Int {
    delay(2000) // Dieser Aufruf ist jetzt erlaubt, weil someCalculation selbst eine Suspending Function ist
    return 1 + 1
}

suspend fun someOtherCalculation(): Int {
    delay(1500)
    return 2 + 2
}
/*
    Der Aufruf von await() funktioniert daher wie folgt:

        Wenn das asynchron berechnete Ergebnis schon vorliegt, dann kann dieses sofort verwendet werden.
        Wenn das asynchron berechnete Ergebnis noch nicht vorliegt, dann wird der aktuelle Arbeitsauftrag unterbrochen, bis das Ergebnis vorliegt. An dieser Stelle erfolgt also eine Synchronisierung der asynchron ablaufenden Berechnungen, um auch wirklich mit den Ergebnissen arbeiten zu können.“

    Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.
*
* */


fun main() {
    // someCalculation() // Dieser Aufruf ist nicht erlaubt, was korrekt ist!. Deshalb auskommentiert

    runBlocking {
        val zahl1: Deferred<Int> = async { someCalculation() } // Problemlos und erlaubt
        val zahl2: Deferred<Int> = async { someOtherCalculation()}
        // Ohne await kann man die Zahlen so nicht abfragen....
        println("Berechnung ist gestartet")
        val result = zahl2.await() + zahl1.await()
        println("Ergebnis ist: $result")
    }
}

// Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.