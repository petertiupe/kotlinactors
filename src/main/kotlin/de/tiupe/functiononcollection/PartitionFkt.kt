package de.tiupe.functiononcollection

/**
 * Die Partition-Funktion zerlegt eine Collection in zwei Teile
 * Ihre Signatur ist
 *
 *  public inline fun <T> Iterable<T>.partition(predicate: (T) -> Boolean): Pair<List<T>, List<T>>
 *
 * daraus ist ersichtlich, dass die Rückgabe aus einem Pair von zwei Listen besteht, die man mit
 * Hilfe des "Destructors" gleich zwei Variablen zuweisen kann, wie im Beispiel geschehen.
 *
 * Die Ausgabe des Programms ist:
 *
 *      Die geraden Zahlen zwischen 1 und 20 sind: [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
 *      Die ungeraden Zahlen zwischen 1 und 20 sind: [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
 *
 * */
fun main(){
    val myList = (1..20).asIterable()
    val (teil1, teil2) = myList.partition { it %2 == 0 }
    println("Die geraden Zahlen zwischen 1 und 20 sind: ${teil1}")
    println("Die ungeraden Zahlen zwischen 1 und 20 sind: ${teil2}")
}