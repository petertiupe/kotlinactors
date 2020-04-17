package de.tiupe.hotandcold

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking{
        flowOf("A", "B", "C")
            // onEach ist eine intermediate Function, daher wird sie nur ausgeführt, wenn
            // auch die Termina-Function (hier collect) ausgeführt wird.
            // kommentiert man collect aus, macht das Programm gar nichts.

            .onEach { println("1$it") }

            // ohne den Buffer ist die Ausführungsreihenfolge
            // 1A 2A 1B 2B 1C 2C

            .buffer()

            // mit dem Buffer ergibt sich:
            // 1A 1B 1C 2A 2B 2C

            .collect { println("2$it") }

            // Der Grund dafür ist, dass der Buffer eine separate Coroutine erzeugt und
            // die Ausführung des collects in dieser zweiten Coroutine stattfindet.
            // Der Buffer baut einen "Standard-Channel zwischen dem emittierenden Flow
            // und dem empfangenden Flow auf

        // In der Dokumentation auf ist dafür eine sehr schöne Grafik, die die Abarbeitung mit
        // Buffer zeigt:
        /*
        P : -->-- [1A] -- [1B] -- [1C] ---------->--  // flowOf(...).onEach { ... }

        |
        | channel               // buffer()
        V

        Q : -->---------- [2A] -- [2B] -- [2C] -->--  // collect

         When operator’s code takes time to execute this decreases the total execution time of the flow.
         A channel is used between the coroutines to send elements emitted by the coroutine P to the
         coroutine Q.
         If the code before buffer operator (in the coroutine P) is faster than the code after buffer operator
         (in the coroutine Q), then this channel will become full at some point and will suspend
         the producer coroutine P until the consumer coroutine Q catches up.
         The capacity parameter defines the size of this buffer.


         */

    }
}