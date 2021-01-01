package de.tiupe.asynchnachbuch

import kotlinx.coroutines.*

/*
* Dies ist ein sehr interessantes Beispiel, weil die sehr lange Liste
* in einzelnen Coroutinen verarbeitet wird. Anschließend wird mit
*
*       AwaitAll
*
* auf das Ergebnis aller Verarbeitungen gewartet ...
*
* Man hat also einen sehr hohen Parallelisierungsgrad.
*
*
* */

fun myLongRunningFunc(i: Int): Int {
    return i + 1
}

fun main() = runBlocking {
    val lst = List<Int>(100){ (0..100_000).random() }
    var result = listOf<Int>()
    runBlocking {
        result = lst.map {
            GlobalScope.async(Dispatchers.Default) {
                println("Start der Verarbeitung")
                println("vorher: $it")
                val erg = myLongRunningFunc(it)
                println("nachher: $erg")
                erg
            }
        }.awaitAll()
    }
    println(result)
}