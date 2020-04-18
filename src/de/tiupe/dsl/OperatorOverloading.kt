package de.tiupe.dsl

/**
 * Operatoren zu überschreiben ist eine perfekte Möglichkeit, eine DSL
 * nach außen wie eine eigene Sprache aussehen zu lassen.
*/
operator fun String.times(n: Int)= repeat(n)

/**
 * Eine Extension Function ist eine Erweiterung auf einem speziellen Typ, oben z.B. auf dem Typ
 * String. Genauso ist eine Funktion vom Typ
 * (T) -> Boolean ein Funktionstyp und kann als Extension-Function geschrieben werden.
 *
 * Man hat also ein Lambda with Receiver wie in dem folgenden Ausdruck
 *
 * val adder: Int.(Int) -> Int = { a -> this + a }
 *
 * Die folgende Funktion liest man folgendermaßen:
 *
 * Erweitere jede Funktion, die einen Typ auf einen Boolean abbildet mit dem not-Opeerator,
 * in dem der Ausdruck des Boolean-Ergebnisses verneint wird.
 *
 * In dem DSL-Kapitel steht dies, weil damit eine allgemeine generische Funktion als
 * Extension-Function existiert.
 * 
 * */
operator fun <T> ((T) -> Boolean).not(): (T) -> Boolean = { !this(it) }

fun isShort(s: String) = s.length<= 4

fun String.isUpperCase() = all { it.isUpperCase() }



//  fun adder1 Int.(Int) -> Int = { a -> this + a }



fun main() {
    /** Weil die Extension-Function mit operator markiert wurde, kann man jezt einfach
        auf dem String "mal 5" aufrufen.
        Das Ergebnis des Programms ist natürlich das Genialste:

            Peter ist der Beste kann man nicht oft genug lesen
            Peter ist der Beste kann man nicht oft genug lesen
            Peter ist der Beste kann man nicht oft genug lesen

     */
    println("\nPeter ist der Beste kann man nicht oft genug lesen" * 5)

    /* Hier noch die Aufrufe der generisch definierten Operator-Fuction */
    val data = listOf("abc", "abcde", "ABCDE", "aBcD", "ab")
    println(data.count(::isShort)) // 3
    println(data.count(!::isShort)) // 2
    println(data.count(String::isUpperCase)) // 1
    println(data.count(!String::isUpperCase)) // 4

}