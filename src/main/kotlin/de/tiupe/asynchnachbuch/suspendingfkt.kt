package de.tiupe.asynchnachbuch

import kotlinx.coroutines.*


/*
* Unterschiede zu "normalen" Funktionen
*
*   1) Die Ausführung einer suspending Fkt kann von außen kontrolliert werden. Die Fkt kann beispielsweise
*      durch launch im Hintergrund ausgeführt werden.
*
*   2) Suspendig FKts können nur in einem Coroutine-Scope ausgeführt werden. Aufrufe aus normalem Code
*      funktionieren nicht.
*
*   3) asyn, launch und delay dürfen in einer supending-Fkt gentzt werden, da in einer Suspending-Fkt ein
*      Coroutine-Scope vorausgesetzt werden kann.
*
*   Bei der Ausführung von asynchronem Code hat man die Wahl, den asynchronen Code als Lambda-Ausdruck
*   auszuführen oder zunächst in Methoden / Funktionen mit dem Schlüsselwort suspend zu deklarieren.
*
*   Lambda-Ausdrücken ist der Vorzug zu geben, wenn es sich um wenige Zeilen Code handelt.
*   Bei längerem Code ist die Ausgliederung in Funktionen sinnvoll.
*
* */

fun main() {
    GlobalScope.launch {
        suspendingWithContext()
    }

    GlobalScope.launch {
        doSomethingExpensive()
    }

    GlobalScope.async {
        val longString = createLongString()
        println("Zufallsstring ist: $longString")
    }


    // ohne keine Anzeige, weil das Programm zu schnell ist...
    Thread.sleep(2_000)

}

suspend fun suspendingWithContext() {
    withContext(Dispatchers.IO) {
        delay(3000)
        println("Dieser Code wird mit dem gewählten Dispatcher ausgeführt, der erst in der Coroutine festgelegt " +
                "wird.")
    }
}

suspend fun doSomethingExpensive() {
    println("Diese Funktion ist eine sehr teure Funktion und erzeugt eine Summe ...")
    println("Summenerzeugung läuft parallel zum Rest des Programms ...")
    var sum = 0L
    for (i in 0..500_000L)
        sum += i
    println("Summe: $sum")
}

suspend fun createLongString(): String {
    println("Die Funktion ist auch teuer und erzeugt einen Zufallsstring ...")
    println("Stringerzeugung läuft ebenfalls parallel zum Restprogramm ...")
    val stringBuilder = StringBuilder()
    for (i in 0..100_000)
        stringBuilder.append("0123456789abcdefg".random())
    return stringBuilder.toString()
}