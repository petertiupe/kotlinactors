package de.tiupe.properties

/**
 * lazy selbst ist eine Funktion, der ein Lambda übergeben wird. Hier die Signatur:
 *
 *      public actual fun <T> lazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)
 *
 * Der Rückgabewert ist also ein Objekt vom Typ Lazy<T>
 * Der erste Aufruf des Getters führt das Lambda aus. Mehr steckt nicht dahinter.
 * */


class LazyInitializationExample(){
    val propertyToInitializeLazy: String by lazy {
        println("jetzt wurde ich aufgerufen")
        "Hallo Peter jetzt bin ich initialisiert"
    }
}

fun main() {
    val objOfClass: LazyInitializationExample = LazyInitializationExample()
    println("Hier mache ich was, die Variable ist noch nicht initialisiert!!!")
    println("Jetzt kommt der erste get-Zugriff auf die Variable, die lazy initialisiert wird:")
    println("Der Wert der Variablen ist: ${objOfClass.propertyToInitializeLazy}")
    println("Das wars schon, so einfach ist das ...")
}