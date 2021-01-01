package de.tiupe.asynchnachbuch

import kotlinx.coroutines.*

/*
* Der Scope legt den Bereich fest, in dem die Logik abläuft.
* Der Scope dient dazu, Bereiche zu bündeln, die zusammenghören.
* Wenn dann etwas abbricht, kann man immer noch den Scope löschen
* und damit alle anderen Resourcen dieses Bereichs.
*
* Der Dispatcher dagegen legt den Thread fest, in dem der Code abläuft.
*
* Vom Standard her gibt es die folgenden Dispatcher:
*
*   Dispatcher.Main         --> Code läuft im Main-Thread
*
*   Dispatcher.IO           --> Code, der auf Dateien oder Netzwerkressourcen zurückgreift
*
*  Dispatcher.Default       --> für CPU intensive Aufgaben, dies ist der Default,
*                               der auch dann verwendet wird, wenn kein Dispatcher
*                               angegeben wird.
*
*   Ich bringe gerne noch die Coroutinen und die suspending-Functions immer in einen
*   Topf.
*   Coroutinen sind erst einmal Kotlins Antwort auf die Nebenläufigkeit, die vollständig
*   von Kotlin verwaltet wird.
*   Suspending-Functions sind die Antwort auf Callbacks etc., also
*   die Antwort auf die Strukturierung von Code.
*   Der Zusammenhang besteht darin, dass suspending-Functions nur innerhalb von anderen
*   suspending-Functions oder aber innerhalb von Coroutinen aufgerufen werden dürfen.
*   Ansonsten gibt es die Fehlermeldung:
*
*   "Suspend function 'delay' should be called only from a
*    coroutine or another suspend function"
*
* */

fun main() {
    // der per launch aufgerufene Code wird parallel ausgeführt.
    GlobalScope.launch {
        println("Ich lege mich 10 Sekunden hin...")
        delay(10_000)
        println("10 Sekunden sind um...")
    }

    // So gibt man einen Dispatcher mit an den Scope
    GlobalScope.launch(Dispatchers.IO) {
        println("Ich lege mich nur 5 Sekunden hin")
        delay(5_000)
        println("5 Sekunden sind um")
    }

    // Bleibt die Frage, wie man einen eigenen Scope erzeugt,
    // dazu erzuegt man eine Suspending function:
    GlobalScope.launch {
        val res = funWithCoroutineScope()
        println("Das Ergebnis ist $res" )
        delay(2)
    }

    println("Hier gehts sofort mit dem Hauptteil weiter")
    println("Anzahl aktiver Threads ist: ${Thread.activeCount()}")
    Thread.sleep(12_000)
    println("Jetzt bin ich am Ende")
}

suspend fun funWithCoroutineScope(): Int {
    val returnValue = coroutineScope<Int> {
        val data: Deferred<Int> = async(Dispatchers.IO) {
            val a: Int  = 1
            val v: Int  = 2
            a + v
        }
        val result: Int = data.await()
        result
    }
    return returnValue
}