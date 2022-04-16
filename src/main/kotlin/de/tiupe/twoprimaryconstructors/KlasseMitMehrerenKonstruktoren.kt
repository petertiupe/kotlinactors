package de.tiupe.twoprimaryconstructors

class KlasseMitMehrerenKonstruktoren {
    var a: String? = null
        set(value) {
        field = value // backing field
    }
    get() = field  // backing field

    constructor(toSet: String) {
        a=toSet
    }

    constructor(toSet: Int) {
        a = toSet.toString()
    }

    constructor()

}

data class MeineDataKlasse private constructor(val a: Int) {

    var b: Int? = null
        set(value) {
            field = value
        }
    get() = field

    constructor(a: Int , b: Int) : this(a) {
        this.b = b
    }
}

fun main() {
    val instanz = KlasseMitMehrerenKonstruktoren()
    println("vorher: ${instanz.a}")
    instanz.a = "Peter wars"
    println("nacher: ${instanz.a}")

    // Beispiel mit drei primären Konstruktoren
    val stringInstanz = KlasseMitMehrerenKonstruktoren("Peter machts")
    val intInstanz = KlasseMitMehrerenKonstruktoren(5)
    println("${intInstanz.a}")
    println("${stringInstanz.a}")

    val inst =  MeineDataKlasse(2, 3)
    println("${inst.copy(3)}")
}