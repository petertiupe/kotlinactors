package de.tiupe

import kotlin.properties.Delegates

class User {
    // Wie bei by lazy wird auch hier ein Lambda an die Property gebunden.
    // Das Lambda muss folgender Signatur gehorchen:
    // In der Klammer kann man den ersten Wert für die Variable setzen, der dann nicht den Aufruf des
    // Obeservables aufruft.
    //
    // der zweite Funktionswert ist der alte Wert
    // der dritte Funktionswert ist der neu zu setzende Wert.
    var name: String by Delegates.observable("<no name>") {
        _, old, new -> println("$old -> $new")
    }

    var p: Int = -1

    var alter: Int by Delegates.vetoable(0) {
            p, old, new -> schreibeConsoleFuerAlter(old, new)
    }

    fun schreibeConsoleFuerAlter(old: Int, new: Int): Boolean{
        println("Hier kommt die Nachricht.... das nichts passiert ist...")
        return new - old <= 1
    }
}

fun main() {
    val user = User()
    user.name = "first"
    user.name = "second"
    user.alter = 42
    println(user.alter)
    user.alter = 43
    println(user.alter)
    user.alter = 50
    println(user.alter)
    println(user.p)
}