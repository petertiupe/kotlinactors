package de.tiupe.coroutinesnachbuch

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun main(args: Array<String>) = runBlocking {
    // Die folgende Ausführung bekommt einen eigenen
    // Thread / Scope für seine Ausführung zugewiesen
    launch {
        delay(2000)
        println("World")
    }

    launch(context = Dispatchers.IO){
        delay(1000)
        println("Peter wars")
    }

    // Auch runBlocking bekommt einen eigenen Coroutine-Context
    // beim Aufruf mit. Somit laufen die beiden Blöcke in unterschiedlichen
    // Scopes, es sei denn, die Laufzeitumgebung weißt beiden denselben zu.

    println("Hello")
    //runBlocking {
    //    delay(2000)
    //}

}

class CoroutineContextImpl : CoroutineContext {
    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
        TODO("Not yet implemented")
    }

    override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? {
        TODO("Not yet implemented")
    }

    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
        TODO("Not yet implemented")
    }

}