package de.tiupe.hansercoroutines.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    // Auftrag A für Hauptarbeiter
    runBlocking {
        // Auftrag B fürs globale Team
        // Auf dem GlobalScope werden Worker-Threads erzeugt... diese Stoppen spätestens, wenn das Hauptprogramm
        // beendet wird.
        GlobalScope.launch {
            repeat(5) {
                println("Moin von ${Thread.currentThread().name}")
            }
        }

        // Auftrag C für mich, den Hauptarbeiter
        // Launch startet auf dem Main-Thread, wenn kein Scope vorangestellt wird...
        launch {
            repeat(5) {
                println("Guten Tag von ${Thread.currentThread().name}")
            }
        }

        // Auftrag D für mich, den Hauptarbeiter
        launch {
            println("Extraausgabe. ${Thread.currentThread().name}")
        }

        println("Alles gestartet. ${Thread.currentThread().name}")

        delay(2000) // Meinen Auftrag A unterbrechen, der Hauptarbeiter
        // kann jetzt seine anderen Aufträge bearbeiten

        println("Fertig. ${Thread.currentThread().name}")
    }

    println("Das Programm ist beendet. ${Thread.currentThread().name} ")
}

// Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.