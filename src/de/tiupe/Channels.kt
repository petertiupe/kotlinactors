package de.tiupe

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

// Warum man von einem Channel an sich selbst schickt, muss ich mir noch klar machen.

fun main() = runBlocking<Unit> {
    val ch = getChannel()
    launch{
        for(x in 1..10)
            ch.send(Pair(x, x * x))
    }

    receiveChannel(ch)
}

suspend fun getChannel(): Channel<Pair<Int, Int>> {
    val channel = Channel<Pair<Int, Int>>()
    return channel
}

suspend fun receiveChannel(channel: Channel<Pair<Int, Int>>): Unit {
    for(y in channel){
        println("Quadrat von ${y.first} ist ${y.second}")
    }
    println("Das wars, mehr Quadrahtzahlen hab ich nicht.")
}