package de.tiupe.channel

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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

    // Der Channel bekommt die Werte des Flows von Int herein und stellt sie einem etwaigen Receiver zur
    // Verfügung.
    val job1 = launch {
        intFlowFunction().collect { value ->
            delay(300) // pretend we are processing it for 300 ms
            channel.offer(value)
        }
    }

    val job2 = launch {
        receiver1.channel.consumeEach { zahl -> println("Receiver 1 meldet die Zahl: $zahl") }
    }
    val job3 = launch {
        receiver2.channel.consume { println("Receiver 2 meldet die Zahl: $this") }
    }

    println("Jetzt fängt die Arbeit erst an ...")
}