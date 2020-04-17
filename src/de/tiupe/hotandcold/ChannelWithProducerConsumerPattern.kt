package de.tiupe.hotandcold

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..100) send(x * x)
}

fun main() {
    runBlocking {
        val squares: ReceiveChannel<Int> = produceSquares()
        squares.consumeEach {
            println(it)
        }
        println("Done!")

    }
}








