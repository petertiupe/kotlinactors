package de.tiupe.asynchnachbuch

import kotlinx.coroutines.*

/*
* Exceptions werden in Coroutinen unterschiedlich behandelt.
*
* 1) launch             --> werden beim Auftreten von Exceptions einfach abgebrochen.
*                           Absicherung des Codes nur innerhalb des asynchronen Codes möglich
*                           auf der Ebene von launch kommt nie eine Fehlerinformation an
*
* 2) async              --> auch in diesem Fall wird die Coroutine einfach abgebrochen
*                           wird auf das Defered-Objekt zugegriffen (z.B.await) wird eine Fehlermeldung ausgelöst
*                           Die Absicherung muss also auf der Ebene von async erfolgen.
*
* 3) Coroutine
*    ExceptionHandler   --> launch oder async wird ein Coroutine Exception-Handler als Parameter mitgegeben
*
*
* Zusammenfassung: je nach Aufruf muss die Absicherung an anderer Stelle erfolgen.
* */


fun main() {
    // Da die ersten beiden Fälle klar sind, hier nur ein Beispiel für den
    // Coroutine-Exception-Handler

    val myExHandler: CoroutineExceptionHandler = CoroutineExceptionHandler {context, exception ->
        println("Fehler vom Handler 1 verarbeitet: $exception")
    }

    val myOtherExHandler: CoroutineExceptionHandler = CoroutineExceptionHandler{context, exception ->
        println("Fehler vom Handler 2 verarbeitet: $exception")
    }

    runBlocking {
        // hier wird der Handler an die Coroutine übergeben.
        GlobalScope.launch(myExHandler) {
            throw Exception("Peter wars")
        }
    }

    runBlocking{
        // Es geht auch mit async
        val x = GlobalScope.async(myOtherExHandler) {
            throw Exception("Peter wars schon wieder...")
        }
        try {
            val wert = x.await()
        } catch(ex: Exception){
            println("Fehler: ${ex.message}")
        }
    }

    println("Hier gehts nach den beiden Fehlern weiter ...")


    println("Und jetzt noch das Beispiel aus der Dokumentation ...")
    documentationExample()


    // damit die Coroutine auch laufen kann, sonst ist das Programm zu schnell zuende.
    Thread.sleep(3_000)
}

/* Auch hier sieht man nicht den Fehler aus dem async Aufruf, d.h. dass auch für den
*  Coroutine-Exception gilt das in der Einleitung dieser Datei im Kommentar gesagte*/
fun documentationExample() = runBlocking{
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
        throw AssertionError()
    }
    val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
        throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
    }
    joinAll(job, deferred)
}
