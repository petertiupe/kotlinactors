package de.tiupe.higherorderfunctionsandlambdas

/**
 * In der Kotlin-Dokumentation steht ein gutes Beispiel, das gleich sehr viele der
 * Eigenschaften von Kotlin-Funktionen zeigt, daher bilde ich es hier ab.
 * Es geht um die Faltung auf einer Collection, die ich hier in eine Faltung auf
 * einem Iterable-Objekt umgewandelt habe.
 *
 *
 * */

/**
 * Die Faltungs ist als Extension-Function realisiert. Parameter sind ein Anfangswert,
 * der Parameter initial und die Funktion, die den Anfangswert mit dem nächsten Wert der
 * Liste / kombiniert.
 *
 * Der Funktionstype wird in Kotlin immer mit Klammern notiert. Der Typ der
 * combine-Funktion ist also:
 *
 *      (R, T) -> R
 *
 * oder für mein konkretes Beispiel in der main-Funktion:
 *
 *      (Int, Int) -> Int
 *
 * Lambdas sind Ausdrücke in geschweiften Klammern ohne Funktionsrumpf.
 * In den Lambdas werden die Parameter nicht in Klammern geschrieben.
 * Auch das kann man sich in der main-Funktion ansehen:
 *
 *      { a: Int, b: Int -> a + b }
 *
 * */
fun <T, R> Iterable<T>.foldForMe(initial: R, combine: (acc: R, nextElement: T) -> R): R {
    var accumulator: R = initial

    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }

    return accumulator
}

/**
 * Als Beispiel für meinen Funktionsaufruf habe ich das Euler-Problem, die Summe der 1. 100 Zahlen
 * durch die Faltung ermitteln lassen.
 * */
fun main(){
    val eulerList = (1..100).asIterable()
    val summe1Bis100 = eulerList.foldForMe(0) {
        a:Int, b: Int -> a + b
    }
    println("Der Aufruf mit Lambda liefert: $summe1Bis100") // 5050

    // ein alternativer Aufruf kann darin bestehen, eine Funktion zu definieren:
    fun add(a: Int, b: Int): Int {
        return (a + b)
    }
    // damit kann man den Aufruf von oben alternativ schreiben:
    val summe1Bis50 = (1..50).asIterable().foldForMe(0, ::add)
    println("Der alternative Aufruf liefert: $summe1Bis50")


}