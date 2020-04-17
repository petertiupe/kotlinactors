package de.tiupe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

/*
* Eine Pipeline ist mit dem Vergleichbar, was
* in Scala ein Stream ist. Eine unendliche Folge
* von Werten.*/
fun main() = runBlocking {
    val squares = produceNumbers()
    // consumeEach ersetzt die for-Schleife auf
    // der Consumer-Seite.

    squares.consumeEach { println(it) }
    println("Done!")
}

/*
* produce-Funktion erzeugt einen Receive-Channel vom Typ Int.
* Der Vorteil ist, dass man dann auf dem Receiver einfach
* consume aufrufen kann. */
fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}