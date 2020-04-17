package de.tiupe.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

/**
 * Hier wird erneut ein Flow erzeugt, indem ein Producer aufgerufen wird.
 * Dieses Beispiel zeigt, wie man die Ablaufumgebung für einen Flow steuern
 * kann.
 * */
val flowA = flow {
    println("Emitting coroutine -> $coroutineContext")
    emit("Tina")
    emit("Inken")
    emit("Lara")
    delay(100)
    emit("Done")
}

/**
 * Der hier erzeugte Flow flowB läuft in der Umgebung von
 * dem Dispatcher.IO-Dispatcher. So hat man also auch die Möglichkeit
 * die Ablaufumgebung für den Flow zu steuern.
 *
 * Die Ausgabe des Programms ist:
 *   Emitting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8]
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Tina
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Inken
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Lara
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Done
 *   Emitting coroutine -> [ProducerCoroutine{Active}@6d8caa40, LimitingDispatcher@9cc47c9[dispatcher = DefaultDispatcher]]
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Tina
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Inken
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Lara
 *   Collecting coroutine -> [BlockingCoroutine{Active}@27f8302d, BlockingEventLoop@4d76f3f8] and value Done
 *
 * Wie man sieht, ist lediglich für die emitting flowB, also für den
 * Producer-Thread von B der Thread ein anderer. Ist auch logisch, weil die
 * collect-Funktionen jeweils in der Hauptfunktion aufgerufen werden.
 *
 */
@ExperimentalCoroutinesApi
val flowB = flowA.flowOn(Dispatchers.IO)

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    flowA.collect{
        println("Collecting coroutine -> $coroutineContext and value $it")
    }

    flowB.collect {
        println("Collecting coroutine -> $coroutineContext and value $it")
    }
}