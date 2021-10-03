package de.tiupe.hansercoroutines.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
Eine Beispielausgabe des Programms sieht so aus.
Alles was hier gezeigt werden soll, ist, dass es mit delay als suspending-Function
möglich ist, weitere Aufträge an den main-Thread zu geben.
mit Thread.sleep... würde hier nichts passieren, sondern erst anschließend die weitere Aktion durchgeführt.

    Moin von DefaultDispatcher-worker-1
    Moin von DefaultDispatcher-worker-1
    Moin von DefaultDispatcher-worker-1
    Extraausgabe. main
    Extraausgabe. main
    Extraausgabe. main
    Guten Tag von DefaultDispatcher-worker-3
    Guten Tag von DefaultDispatcher-worker-3
    Guten Tag von DefaultDispatcher-worker-3

    * */
    fun main() {
        // ...
        runBlocking {
            // Coroutinen im globalen Scope, wie gehabt:
            GlobalScope.launch {
                repeat(1000) { println("Moin von ${Thread.currentThread().name}") }
            }

            GlobalScope.launch {
                repeat(1000) { println("Guten Tag von ${Thread.currentThread().name}") }
            }

            // Zusätzliche Coroutine im Scope von runBlocking
            this.launch {
                repeat(3) { println("Extraausgabe. ${Thread.currentThread().name}") }
            }
            this.launch {
                druckMich()
            }
            println("Alles gestartet. ${Thread.currentThread().name}")
            delay(2000)
                println("Fertig. ${Thread.currentThread().name}")
            }


        println("Das Programm ist beendet. ${Thread.currentThread().name}")
    }

suspend fun druckMich(){
    println("Peter wars")
}


    // Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.

