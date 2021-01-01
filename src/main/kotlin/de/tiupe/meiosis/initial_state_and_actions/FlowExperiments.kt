package de.tiupe.meiosis.initial_state_and_actions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow { // flow builder
    for (i in 1..3) {
        emit(i) // emit next value
        delay(500) // pretend we are doing something useful here
    }
}

suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun quietSimple(): Flow<String> {
    return (
        (1..3).asFlow() // a flow of requests
            .transform { request ->
                emit("Making request $request")
                emit(performRequest(request))
                emit("Pups")
                emit(request.toString())
            }
    )
}


fun main() = runBlocking<Unit> {

    // Flows selbst sind nicht suspending sondern cold. Sie werden erst ausgewertet,
    // wenn sie z.B. via collect ausgewertet werden...

    // Launch a concurrent coroutine to check if the main thread is blocked
    // launch startet eine weitere Coroutine, sodass der Thread weiterläuft
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }
    // Collect the flow
    val simpleVal: Flow<Int> = simple()
    simpleVal.collect { v -> println(v) }
    // diese Zeile wird erst nach allen anderen gedruckt, weil collect eine
    // suspending-Function ist und somit wartet, bis der Flow sein Signal schickt, dass
    // nichts mehr kommt....
    // Auf der anderen Seite ist der Flow solange kalt, bis ein collect aufgerufen wird.
    // Intermediate Flow-Functions wie map und andere sind ebenfalls kalt. Sie sind nicht
    // terminierend und können daher sofort einen neuen Flow zurückgeben. Erst der Aufruf
    // der Terminal-Funktionen sorgt für die Abarbeitung der Flows.

    val quietSimpleVal: Flow<String> = quietSimple()
    quietSimpleVal.collect {  a -> println(a) }
    println("Peter wars")
}