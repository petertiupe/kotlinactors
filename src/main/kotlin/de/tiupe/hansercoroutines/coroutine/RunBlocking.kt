package de.tiupe.hansercoroutines.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    /*
        Es werden zwei Coroutinen auf dem Global-Scope gestartet, die ohne den runBlocking-Teil schon beendet würden,
        da die beiden Coroutinen auf einem Worker-Thread gestartet werden, die Kontrolle über das Programm aber beim
        main-Thread liegt.

        Moin von DefaultDispatcher-worker-2
        Moin von DefaultDispatcher-worker-2
        Auch außerhalb von runBlocking arbeitet der  main
        Das Programm ist beendet. main



        - Thread. sleep(2000) blockiert den Thread, bis 2000 ms um sind.
        - delay unterbricht die Ausführung des aktuellen Codeblocks und würde anderen Koroutinen innerhalb
          desselben Scopes die Möglichkeit geben, ihre Arbeit zu verrichten.
        - runBlocking sorgt dafür, dass der Thread blockiert, bis alle Aufträge des Scopes vollständig ausgeführt sind.
          Da hier nur ein einziger Auftrag festgelegt ist, nämlich die Unterbrechung von 2000 ms,
          führt runBlocking dazu, dass 2000 ms lang nichts innerhalb dieses Scopes geschieht und das Programm
          erst nach 2000 ms beendet. Die im globalen Scope gestarteten Koroutinen laufen jedoch nebenläufig weiter.

          Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“ Apple Books.
    */

    GlobalScope.launch {
        repeat(200) { println("Moin von ${Thread.currentThread().name}") }
    }

    GlobalScope.launch {
        repeat(200) { println("Guten Tag von ${Thread.currentThread().name}") }
    }

    runBlocking {
        println("Hauptthread ist ${Thread.currentThread().name}")
        delay(2000)
    }

    println("Auch außerhalb von runBlocking arbeitet der  ${Thread.currentThread().name}")
    println("Das Programm ist beendet. ${Thread.currentThread().name} ")
}
