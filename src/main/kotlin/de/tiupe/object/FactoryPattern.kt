package de.tiupe.`object`

/**
 * In der Data Objects habe ich gezeigt, wie man eine Factory erstellen kann.
 * Die dort gezeigte Factory hat allerdings noch den Nachteil, dass die Klasse,
 * deren Instanzen erzeugt werden sollen, noch einen öffentlichen Kontruktor hat.
 *
 * Hier zeige ich eine Factory, die die Erzeugung der Objekte nur noch über die
 * Factory möglich macht.
 * */



class InstancesToCreate private constructor(val name: String) {
    companion object Factory{
        fun createInstance(name: String): InstancesToCreate {
            return InstancesToCreate(name)
        }
    }
}

fun main() {
    // Das Companion-Objekt hat die Möglichkeit auf den privaten Konstruktor zuzugreifen.
    // Man hat so also nur noch über die Factory die Möglichkeit, eine Instanz zu erzeugen.
    val instance: InstancesToCreate =InstancesToCreate.createInstance("Peter wars")
    println("Auf den Namen kann man zugreifen: ${instance.name}")

    //InstancesToCreate("Peter wars")
    println("\nSo kann man eine Instanz der Klasse jetzt nicht mehr erzeugen: InstancesToCreate(\"Peter wars\")")
}