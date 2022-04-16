package de.tiupe.hansercoroutines.coroutinescope
/* Die verschiedenen Coroutine Builder
        launch dient dazu, um aus einer existierenden Koroutine heraus weitere Koroutinen zu erzeugen.

        Mit GlobalScope.launch können Sie eine Koroutine im globalen Scope erzeugen.

        runBlocking wird eingesetzt, um aus einem regulären, eventuell blockierenden Programmablauf in
        einen Koroutinen-Scope zu wechseln. Erst wenn alle Koroutinen innerhalb von runBlocking vollständig
        abgearbeitet sind, wird in den regulären Programmablauf zurückgekehrt.
        Wenn auf die Abarbeitung aller Koroutinen gewartet werden muss, weil etwa noch eine Serverantwort aussteht,
        dann wird der Thread, der runBlocking aufgerufen hat, blockiert, bis alles erledigt ist.

        runBlocking erzeugt einen eigenen CoroutineScope und sorgt dafür, dass erst nach Beendigung
        aller darin gestarteten Koroutinen im Hauptprogramm fortgefahren wird.



Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.
*/
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Ein eigener Scope
fun main() {
    // erzeugt automatisch einen CoroutineScope CS1
    runBlocking {
        // startet eine Koroutine in diesem Scope (this bzw. CS1)
        launch {
            delay(200)
            println("Ich gehöre zu Scope 1 von runBlocking")
        }

        // Erzeugt einen zusätzlichen CoroutineScope CS2
        ////////////////////////////////////////////////

        coroutineScope {
            // Neuer Arbeitsauftrag X für CS2
            launch {
                println("Ha, ich bin ein weiterer Unterauftrag X in Scope 2")
                delay(400)
            }
                println("Wieder Unterauftrag X in Scope 2")
            }

            // Dies wird als erstes ausgeführt
            println("Ich bin der Hauptauftrag in Scope 2")
            delay(100) // jetzt kann Auftrag X starten
            println("Fortsetzung Hauptauftrag Scope 2")
        }

        // Diese Ausgabe erfolgt in CS1, nachdem alle Aufträge aus
        // CS2 abgearbeitet sind, also als Letztes.
        println("Alle Aufträge aus CS 2 sind fertig.")
    }


// Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.