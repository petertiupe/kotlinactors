package de.tiupe.functiononcollection


/**
 * Die zipWithNext-Funktion macht aus einer Liste Paare aus aufeinander folgenden
 * Werten. Die Länge der Liste wird dabei bei jedem Aufruf um einen Wert kleiner.
 *
 * Hier die Ausgabe des Programms:
 *
 *      Das Ergebnis des zipWithNext-Aufrufs ist:
 *      1 --> 2
 *      2 --> 3
 *      3 --> 4
 *      4 --> 5
 *      5 --> 6
 *      6 --> 7
 *      7 --> 8
 *      8 --> 9
 *
 *      Es gibt ein paar Varianten der Funktion, die bei der Nutzung immer einen Blick lohnen.
 *      Immer werden jedoch die Nachfolger einer Liste miteinander verknüpft.
 *
 * */
fun main(){
    val myList = listOf<Int>(1,2,3,4,5,6,7,8,9)
    val resultList = myList.zipWithNext()

    println("Das Ergebnis des zipWithNext-Aufrufs ist:")
    resultList.forEach {
        println("${it.first} --> ${it.second}")
    }

    println("\nEs gibt ein paar Varianten der Funktion, die bei der Nutzung immer einen Blick lohnen.")
    println("Immer werden jedoch die Nachfolger einer Liste miteinander verknüpft.")


}