package de.tiupe

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/*
* */
fun main() = runBlocking {
    val squares = produceSquares()
    // consumeEach ersetzt die for-Schleife auf
    // der Consumer-Seite.

    squares.consumeEach { println(it) }
    println("Done!")
}

/*
* produce-Funktion erzeugt einen Receive-Channel vom Typ Int.
* Der Vorteil ist, dass man dann auf dem Receiver einfach
* consume aufrufen kann. */
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..10) send(x * x)
}