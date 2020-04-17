package de.tiupe.flows
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay


/*
* Hier sieht man auf unterster Ebene, wie ein Flow arbeitet. Die Funktion
* flow hat folgende Signatur:
*
*   public fun <T> flow(@BuilderInference block: suspend FlowCollector<T>.() -> Unit): Flow<T>
*
* Es handelt sich also um einen Producer für Flows. Innerhalb des Codeblocks
* wird auf einem Flow-Collector gearbeitet, was ich erst etwas verwirrend fand.
* Ein Collector hat die Funktion "emit", erwartet hätte ich eher, dass er eine Funktion
* collect hat, diese ist jedoch auf dem Flow - Interface definiert.
* */
val myFlow = flow {
    emit("Tina")
    emit("Peter")
    emit("Lara")
    emit("Inken")
    emit(1)
    delay(5000)
    emit("Done")
}

/*
* Das Hauptprogramm für das Beispiel. Im Flow wird nichts anderes gemacht,
* als collect aufzurufen. Wichtig an dieser Stelle. Flows sind kalt, es wird
* kein Gebrauch von suspending-Functions gemacht. Prizipiell arbeitet ein Flow so,
* dass erst bei dem Aufruf von collect die Funktion aufgerufen wird, die hinter
* dem "Flow-Collector" steckt, also hier die einzelnen emit-Funktionen.
*
* Was man an dem Beispiel auch sieht ist, dass man den flow mehrfach nutzen kann, sowohl
* die count-Funktion als auch die collect-Funktion starten die Ausgabe jedes Mal neu.
*
* Die Ausgabe des Programms ist sehr einfach:
*
* 6
* Tina
* Peter
* Lara
* Inken
* 1
* Done
* Tina
* Peter
* Lara
* Inken
* 1
* Done
* 6
*
* */
fun main() = runBlocking<Unit> {
    println(myFlow.count())

    myFlow.collect {
        println(it)
    }

    myFlow.collect {
        println(it)
    }

    println(myFlow.count())
}






