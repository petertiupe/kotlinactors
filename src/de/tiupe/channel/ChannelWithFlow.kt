package de.tiupe.channel

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun intFlowFunction(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(1000) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }

}

class ChannelReceiver(val channel: ConflatedBroadcastChannel<Int>)

fun main() = runBlocking {
    val channel = ConflatedBroadcastChannel<Int>()

    // Ein channel sollte mehrere Receiver besitzen können.
    val receiver1 = ChannelReceiver(channel)
    val receiver2 = ChannelReceiver(channel)
    val receiver3 = ChannelReceiver(channel)

    // Der Channel bekommt die Werte des Flows von Int herein und stellt sie einem etwaigen Receiver zur
    // Verfügung.
    val offerJob = launch {
        // Der Channel stellt immer nur den aktuellsten Wert zur Verfügung.
        intFlowFunction().collectLatest { value ->
            delay(300) // pretend we are processing it for 300 ms
            channel.offer(value)
        }
        // Der Code in der Coroutine läuft synchron. Solange noch Werte aus dem Flow
        // kommen, sendet der Channel. Die unten per launch-gestarteten Jobs stoppen
        // nur, wenn der channel ein close sendet.
        channel.close()
    }

    val receiver1Job = launch {
        receiver1.channel.consumeEach { println("Receiver 1 meldet die Zahl: $it") }
    }

    val receiver2Job = launch {
        receiver2.channel.consumeEach { println("Receiver 2 meldet die Zahl: $it") }
    }

    // So kann man auf der Empfängerseite wieder einen Flow zur Verfügung stellen.
    // Der Flow kann dann wieder wie gewohnt verarbeitet werden, hier mit einem collect,
    // damit er auch terminiert.
    val receiverToFlowJob = launch {
        val flowOfIntFromChannel: Flow<Int> = receiver3.channel.asFlow()
        flowOfIntFromChannel.collect {println("Aus demFlow von Receiver 3 kommt: $it")}
    }

    println("Jetzt fängt die Arbeit erst an ... allerdings weiter oben :-) !")
}