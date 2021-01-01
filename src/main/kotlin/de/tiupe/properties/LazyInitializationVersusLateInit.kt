package de.tiupe.properties

/**
 * lazy selbst ist eine Funktion, der ein Lambda übergeben wird. Hier die Signatur:
 *
 *      public actual fun <T> lazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)
 *
 * Der Rückgabewert ist also ein Objekt vom Typ Lazy<T>
 * Der erste Aufruf des Getters führt das Lambda aus. Mehr steckt nicht dahinter.
 *
 * lateinit ist gegenüber by lazy eine Möglichkeit unnötige Null-Checks zu vermeiden, z.B.
 * dann wenn man mit Dependency-Injection arbeitet.
 * Der lateinit Parameter muss innerhalb der Klasse  (nicht in dem
 * primären Konstruktor) und als notNullable definiert sein. Außerdem muss es sich
 * um ein var-Feld handeln, da ja zunächst die Instanz der Klasse mit einem leeren Feld angelegt wird
 * welches dann später gefüllt wird.
 *
 *
 * */


class LazyInitializationExample(){
    val propertyToInitializeLazy: String by lazy {
        println("jetzt wurde ich aufgerufen")
        "Hallo Peter jetzt bin ich initialisiert"
    }

    lateinit var propertyToInitializeLate: String

    fun isLateInitInitialized(): Boolean {
        return ::propertyToInitializeLate.isInitialized
    }

    // Hier wird der Wert der lateinit-Var gefüllt.
    // Wichtig ist: Um die lateinit-Var muss sich der Entwickler selber kümmern, die
    // Initialisierung durch "by lazy" erfolgt automatisch beim ersten Zugriff.
    fun initLateInitVariable(){
        propertyToInitializeLate = "Das hättest Du mir auch gleich zuweisen können :-)"
    }
}

fun main() {
    val objOfClass: LazyInitializationExample = LazyInitializationExample()
    println("Hier mache ich was, die Variablen sind noch nicht initialisiert!!!")
    println("Jetzt kommt der erste get-Zugriff auf die Variable, die lazy initialisiert wird:")
    println("Der Wert der Variablen ist: ${objOfClass.propertyToInitializeLazy}")
    println("Das wars schon, so einfach ist das ...\n\n")

    println("Jetzt noch der Zugriff auf die lateinit-Variable.")
    // um zu testen, ob die Variable gefüllt ist, sollte man immer die folgende Syntax verwenden:
    // Allerdings kann man diesen Check nur in derselben Klasse oder auf Toplevel-Ebene der Datei
    // verwenden, in der die Klasse definiert ist.
    // Daher ist dieser Code in die Klasse umgezogen worden.

    // if(objOfClass::propertyToInitializeLate.isInitialized){
    //     println("Der Wert der lateinit Variablen ist: ${objOfClass.propertyToInitializeLate}")
    // }
    // Jetzt kann man die Funktion aus der Klasse verwenden:
    if(objOfClass.isLateInitInitialized()){
        println("Die lateinit Variable hat einen Wert und zwar:\n" +
                " ${objOfClass.propertyToInitializeLate}")
    } else {
        println("Die lateinit Variable hat noch keinen Wert")
    }

    objOfClass.initLateInitVariable()

    if(objOfClass.isLateInitInitialized()){
        println("Die lateinit Variable hat einen Wert und zwar:\n" +
                "${objOfClass.propertyToInitializeLate}")
    } else {
        println("Die lateinit Variable hat noch keinen Wert")
    }
}