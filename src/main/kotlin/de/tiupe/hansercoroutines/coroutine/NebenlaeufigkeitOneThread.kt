package de.tiupe.hansercoroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
*   Das Programm arbeitet folgendermaßen:

        Lege einen weiteren Arbeitsauftrag an („Moin“ 5 x ausgeben).
        Lege einen weiteren Arbeitsauftrag an („Guten Tag“ 5 x ausgeben).
        Gib „Ready.“ aus.
        Blockiere, bis alle von mir angelegten Arbeitsaufträge fertiggestellt sind.
        Gib „Das Programm ist beendet.“ aus.

    Die Ausgabe des Programms ist:
        Ready.
        Moin von main
        Moin von main
        Moin von main
        Moin von main
        Moin von main
        Guten Tag von main
        Guten Tag von main
        Guten Tag von main
        Guten Tag von main
        Guten Tag von main
        Das Programm ist beendet.


Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.
* */

fun main() {
    runBlocking {
        launch {
            repeat(5) { println("Moin von ${Thread.currentThread().name}") }
        }

        launch {
            repeat(5) { println("Guten Tag von ${Thread.currentThread().name}") }
        }

        println("Ready.")
    }

    println("Das Programm ist beendet.")
}

// Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.