package de.tiupe.hansercoroutines.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    // Arbeitsauftrag A muss komplett erledigt werden
    runBlocking {
        // Arbeitsauftrag B an das globale Arbeiter-Team senden:
        val moinJob = GlobalScope.launch {
            repeat(5000) {
                println("Moin von ${Thread.currentThread().name} und $it")
            }
        }

        // Erstelle für mich selbst einen Arbeitsauftrag C
        launch {
            repeat(5) {
                println("Guten Tag von ${Thread.currentThread().name}")
            }
        }

        // Erstelle für mich selbst einen Arbeitsauftrag D
        launch {
            println("Extraausgabe. ${Thread.currentThread().name}")


        }

        println("Alles gestartet. ${Thread.currentThread().name}")

        // Warte, bis Auftrag B (moinJob) erledigt ist
        // In der Zeit kann ich C und D erledigen, wenn ich beides schaffe
        // Dies ist der Synchronisationspunkt. Erst wenn der Worker-Thread von B durchgelaufen ist,
        // geht es mit dem Programm weiter.
        moinJob.join()

        // Erst nachdem B erledigt ist, geht es weiter
        // ===========================================
        println("Fertig. ${Thread.currentThread().name}")

        // Falls ich während des Wartens nur C erledigt habe, würde jetzt noch D gemacht werden

    } // Ende von Arbeitsauftrag A

    // Erst wenn Arbeitsauftrag A komplett erledigt ist, geht es hier weiter:
    println("Das Programm ist beendet. ${Thread.currentThread().name} ")
}

// Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.