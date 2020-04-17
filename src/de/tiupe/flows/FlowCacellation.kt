package de.tiupe.flows
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*

/**
* Diese Funktion habe ich schon ausführlich in der Definition der Variablen
* [de.tiupe.flows.myFlow] ausführlich dokumentiert.
*
* */
val flow = flow {
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}

/**
 * Es erfolgt der Aufruf der Hauptfunktion.
 * Der einzige Unterschied zu der main-Funktion
 * aus der Datei de.tiupe.flows.FirstFlow
 * Hier wird lediglich der Job, der aus dem
 * launch-Aufruf resultiert nach einem
 * delay von 400 Millisekunden abgebrochen, sodass nicht
 * der gesamte Flow zur Ausgabe kommt.
 * Die Ausgabe des Programms ist:
 *
 * Hab Dich -> 1
 * Hab Dich -> 2
 * Hab Dich -> 3
 * Done
 *
 * */
fun main() = runBlocking {

    val job = launch {
        flow.collect {
            println("Hab Dich -> $it")
        }
    }
    delay(400)
    job.cancel()
    println("Done")
}


