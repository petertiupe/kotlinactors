package de.tiupe.dsl

/**
 * So wie man in Scala eine beliebige Apply - Methode definieren kann, so kann man dies
 * in Kotlin mit der invoke-Funktion machen.
 * In diesem Beispiel wird die get Funktion genutzt um an Elemente einer Map über den
 * Zugriff via
 *
 *      nameDerMap(key) zu kommen.
 *
 * Achtund, die Extension-Function ist eine Operator-Function
 * */
operator fun <K, V> Map<K, V>.invoke(key: K) = get(key)

/**
 * Die main-Funktion zeigt, wie die invoke - Funktion ähnlich zu der
 * Scala-apply-Funktion dazu genutzt werden kann, einen Aufruf
 * auf dem Namen eines Objekts zu machen.
 *
 * Die Ausgabe des Programms ist wie immer gleich hinter den entsprechenden
 * Zeilen als Kommentar beigefügt.
 * */
fun main () {
    val petersMap = mapOf("I" to 1, "V" to 5, "X" to 10)
    println(petersMap("V")) // 5
    println(petersMap("L")) // null
}