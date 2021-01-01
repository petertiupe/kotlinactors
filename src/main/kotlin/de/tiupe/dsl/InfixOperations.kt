package de.tiupe.dsl
/**
 * Ein weiteres gut geeignetes Sprachmittel für DSLs sind die infix-Functions
 * Auch sie werden hier wieder als Lambda with Receiver mit Typeparameter
 * aufgeschrieben und sind somit auf jedes Funktionspaar anwendbar, das von
 * einem Typ T einen Boolean zurückgibt.
 *
 * */

infix fun <T> ((T) -> Boolean).and( other: (T) -> Boolean ): (T) -> Boolean {
    return { this(it) && other(it) }
}

infix fun <T> ((T) -> Boolean).or( other: (T) -> Boolean ): (T) -> Boolean {
    return { this(it) || other(it) }
}

/**
 * Hier noch die zwei Exentsion-Functions, die durch den obigen Code "verbunden" werden
 * */
private fun isShort(s: String) = s.length<= 4

private fun String.isUpperCase() = all { it.isUpperCase() }

/**
 * Erst hier in der main-Funktion sieht man den eigentlichen Nutzen der "DSL"-Funktionen.
 * Man kan sie schreiben wie "normale" Sprache and und or sehen nicht mehr
 * aus wie Funktionsaufrufe sondern wie normale Sprache und das ist den
 * Infix-Funktionen zu verdanken.
 *
 * Die Ausgabe des Programms steht bereits hinter den einzelnen Programmzeilen als
 * Kommentar.
 * */
fun main() {
    val data = listOf("abc", "abcde", "ABCDE", "aBcD", "ab")

    println(data.count(::isShort and String::isUpperCase)) // 0

    println(data.count(::isShort or String::isUpperCase)) // 4

    println(data.count(!::isShort or String::isUpperCase)) // 2

    println(data.count(!(::isShort and String::isUpperCase))) // 5

}



