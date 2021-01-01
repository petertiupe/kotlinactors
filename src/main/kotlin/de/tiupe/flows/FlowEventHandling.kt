package de.tiupe.flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

// Imitate a flow of events
fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(300) }

fun main() = runBlocking<Unit> {
    onEachEventHandling()
    launchInEventHandling(this)

}

private suspend fun onEachEventHandling() {
    events()
            .onEach { event -> println("Event: $event") }
            .collect() // <--- Collecting the flow waits
    println("Done")
}

private suspend fun launchInEventHandling(cs: CoroutineScope) {
    events()
            .onEach { event -> println("Event: $event") }
            .launchIn(cs) // <--- Launching the flow in a separate coroutine
    println("Done")
}