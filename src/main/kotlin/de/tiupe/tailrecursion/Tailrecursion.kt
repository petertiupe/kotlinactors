package de.tiupe.tailrecursion

/**
 *
 * Bei einer tailrekursiven Funktion muss der rekursive Call
 * immer der letzte in der Funktion sein.
 *
 * Das Schlüsselwort scheint hier anders zu arbeiten als bei Scala,
 * denn es gibt keinen Compilerfehler, wenn man das Schlüsselwort an eine
 * Funktion schreibt, die gar nicht tailrekursiv ist.
 *
 * Es scheint also mehr als ein Compilerhinweis zu sein.
 * Kotlin gibt keinen Fehler zurück, sondern lediglich eine
 * Warnung, dass die Funktion nicht tailrekursiv ist.
 *
 * Der Compiler wandelt die Rekursion bei einer echten tailrekursiven Funktion
 * in einen Loop um.
 *
 * */

fun recursivAberNichtTailRekursiv(n: Long): Long {
    return if (n == 1L) {
        1
    } else {
        // Da hier nicht nur der Aufruf der Funktion kommt, sondern auch noch einmal
        // die Multiplikation mit dem Wert gemacht wird, ist die Funktion nicht
        // tailrekursiv.
        val a = recursivAberNichtTailRekursiv(n - 1)
        n * a
    }
}

tailrec fun tailRekursiv(n: Long, akkumuliert: Long = 1): Long {
    val bisJetzt = n * akkumuliert
    return if (n == 1L) {
        bisJetzt
    } else {
        tailRekursiv(n - 1, bisJetzt)
    }

}


fun main() {
    // Hier kommen die Aufrufe ....
    val nichtTailRec = recursivAberNichtTailRekursiv(10)
    val tailRec = tailRekursiv(10)

    println(nichtTailRec)
    println(tailRec)
}