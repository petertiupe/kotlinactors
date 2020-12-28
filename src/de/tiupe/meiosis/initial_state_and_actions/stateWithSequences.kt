package de.tiupe.meiosis.initial_state_and_actions

/*
* Von den Sequences (analog zu den Java-Streams) werden lediglich zwei wichtige Funktionen genutzt,
*
* In der Meiosis Doku steht es geschrieben:
*
*   "A stream is a sequence of values, similar to an array ...  "
*
* Wenn es eine Sequence ist, kann man sie ja auch gleich so nennen :-)
*
*   1. map
*   2. terminal-Funktion. take selbst ist keine Terminalfunktion, aber z.B. toList etc.
*   3. um eine Sequece zu erweitern gibt es die plus-Funktionen
*   4. es wird eine Funktion benötigt, die wie fold immer einen weiteren Wert aus
*      der Sequence nimmt und ihn mit dem Ergebnis einer vorherigen Verarbeitung
*      weiterverarbeitet.
*
* */
fun main() {
    println("Sequences helfen beim Statusübergang, wenn mehrere Status vorhanden sind.")
    println(endlessInts.takeWhile { it < 10 }.toList())
    val longerSeq = discreteInts.plus(10).plus(11)
    val iter = longerSeq.iterator()
    while(iter.hasNext()){
        println(iter.next())
    }

    val str = emptySequence<Int>()
    str.plus(0)
    str.plus(1)
    str.plus(2)

    val discreteSum = discreteInts.fold(0){a, b -> a + b}
    val endlessSum: Sequence<Int> = endlessInts.drop(1)

    // Kotlin hat für die Sequences eine eigene scan-Funktion, die genauso funktioniert, wie
    // in dem Meiosis-Tutorial beschrieben. Diese gibt es aber erst seit Kotlin 1.4

    val sumOfInts: Sequence<Int> = endlessInts.scan(0){ a, b -> a + b}

    println("Summe der ersten hundert Integerzahlen: ${sumOfInts.take(10).toList()}")






}

val endlessInts = generateSequence(0) { it + 1 }

var discreteInts: Sequence<Int> = endlessInts.take(10)


