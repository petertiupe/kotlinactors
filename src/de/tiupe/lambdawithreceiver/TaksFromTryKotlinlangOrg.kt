package de.tiupe.lambdawithreceiver

/**
 * Damit sich die Syntax ein bisschen setzt, hier noch die Aufgabe zu den Function Literals with
 * Receiver von der Seite try.kontlinlang.org
 *
 *  fun task(): List<Boolean> {
 *      val isEven: Int.() -> Boolean = { this % 2 == 0 }
 *      val isOdd: Int.() -> Boolean = { this % 2 != 0 }
 *
 *      return listOf(42.isOdd(), 239.isOdd(), 294823098.isEven())
 *  }
 *
 * */

fun task(): List<Boolean> {
    // Es werden Function-Literals benutz, daher also Function-Types with Receiver
    val isEven: Int.() -> Boolean = { this % 2 == 0 }
    val isOdd: Int.() -> Boolean = { this % 2 != 0 }

    // Auch hier hätte man das Ganze wieder mit der Notation aus der Datei
    // ExtensionFunctionVersusLambdaWithReceiver schreiben können

    val isEven1: Int.() -> Boolean = fun Int.(): Boolean { return this %2 == 0 }
    val isOdd1: Int.() -> Boolean = fun Int.(): Boolean { return this %2 != 0 }

    return listOf(42.isOdd(), 43.isOdd1(), 239.isOdd(), 46.isEven1(), 294823098.isEven())

}

// hier wird lediglich die oben definierte Funktion aufgerufen.
fun main(){
    val myBools = task()
    for(a in myBools) {
        println(a)
    }
}

