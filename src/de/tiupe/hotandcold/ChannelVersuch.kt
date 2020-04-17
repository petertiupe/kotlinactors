package de.tiupe.hotandcold

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() {
    runBlocking {
        val channel = Channel<Int>()
        launch {
          setupSender(channel)
        }
        launch {
            setupReceiver(20, channel)
        }

        println("Hier holt sich das Hauptprogramm Daten...")
        setupReceiver(20, channel, 2)

        println("Fertig, mehr wollte ich nicht, muss nur noch aufräumen.")
        coroutineContext.cancelChildren()
        println("Aufgeräumt und Tschüss" )
    }
}

suspend fun setupSender(channel: Channel<Int>) {
    for (x in 1..10) {
        delay(1000)
        channel.send(x * x)
    }
}

suspend fun setupReceiver(numberOfValuesToReceive: Int, channel: Channel<Int>, identifier: Int = 1){
    repeat(numberOfValuesToReceive) {
         if(channel.isClosedForReceive) {
            println("Vom Channel kommt nichts mehr")
         } else {
            println("Der Receiver $identifier fragt ab: ${channel.receive()}")
         }
    }
}
