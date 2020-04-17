package de.tiupe.lambdawithreceiver


suspend fun main() {
    // Lambda with reiceiver dient dazu, ein Lambda zu einer Klasse zu speichern.
    // So wie man eine Extension-Funktion zu einer Klasse schreiben kann, kann man auch
    // ein Extension-Lambda zu einer Klasse schreiben.

    // ExtensionFunction
    fun Int.addiere(a: Int) = this + a

    // eine einfache Funktion in Kotlin, weil ich mich noch an die Syntax gewöhnen muss:
    val addierer: (Int, Int) -> Int =  {a, b -> a + b }

    // und jetzt als Lambda with receiver:
    val adder: Int.(Int, Int) -> Int = {b, c -> this + b + c}

    val aufruf = 5.adder(6, 7)
    println(aufruf) // Ergebnis ist an dieser Stelle 18
    
}