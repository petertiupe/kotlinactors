package de.tiupe.scopefunctions

/**
 * Es gibt in Kotlin fünf sogenannte Scope-Functions:
 *
 *      with
 *      run
 *      let
 *      apply
 *      also
 *
 * Am einfachsten erklären sie sich durch ihre Signaturen:
 *
 *      public inline fun <T, R> with(receiver: T, block: T.() -> R): R
 *
 *
 * */

fun main(args: Array<String>) {
    // also:    inline fun T.also (block: (T)  -> Unit): T
    // apply:   inline fun T.apply(block: T.() -> Unit): T

    val myStudent: Student = Student().also{
        // also kennt 'it'
        it.nachname = "Marx"
        it.vorname  = "Peter"
        println(it)
    }.apply {
        // apply kennt 'this', kann also auch ohne auf die
        // Parameter zugreifen...
        this.vorname = "Tina"
        this.nachname ="Feddersen"
        println(this)
    }
    // let bildet auf einen anderen Typ ab, kann ansonsten
    // auch auf das Objekt zugreifen.
    val nachnameB: String? = myStudent.let {
        it.nachname
    }
    nachnameB.also{println(it)}

    // run ist wie let, nur man kann wieder auf this zugreifen:
    val vornameA: String? = myStudent.run {
        this.vorname
    }
    vornameA.run{println(this)}

    // In dem Block kann man auf die Funktionen des
    // übergebenen Objekts zugreifen....
    with(myStudent){
        println(nachname)
        println(vorname)
    }
}

class Student(var nachname: String? = null, var vorname: String? = null){
    override  fun toString(): String {
        return("Vorname: $vorname, Nachname: $nachname")
    }
}