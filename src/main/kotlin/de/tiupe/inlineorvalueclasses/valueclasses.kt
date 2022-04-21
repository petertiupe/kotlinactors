package de.tiupe.inlineorvalueclasses

/**
 * Motivation aus der Kotlin-Dokumentation:
 *
 * Oft werden Wrapper-Klassen benötigt. Aus Compiler- und Speichersicht benötigen
 * diese einen deutlichen Overhead, den man mit den Inline-Classes versucht wieder in den
 * Griff zu bekommen. Noch größer ist dieser Effekt bei den nativen Typen, weil es für
 * sie eine Reihe von Compiler-Optimierungen gibt. Wrapper um native Typen erfahren diese
 * Compileroptimierungen nicht.
 *
 * Um dieses Problem anzugehen, gibt es in Kotlin seit der Version 1.5 die
 *
 *      inline classes
 *
 * Inline classes müssen genau eine einzelne Property im Konstruktor haben und werden zur Laufzeit
 * durch diesen Wert repräsentiert. vars sind nicht möglich.
 *
 * Zur Laufzeit wird keine echte Instanz angelegt, sondern es existiert lediglich der String
 * in einer Variablen. Das ist die Idee dahinter. Zur Laufzeit wird der Code "inlined", genauso
 * wie inline-Funktionen oder inline-Data.
 *
 * Inline-Klassen unterstützen folgende Eigenschaften von regulären Klassen:
 *      Properties und Funktionen können deklariert werden
 *      es gibt einen init-Block
 *
 * Die Funktionen werden zur Laufzeit als statische Methoden zur Verfügung gestellt.
 * So funktionieren die Laufzeitoptimierungen weiter.
 *
 * Inline Klasses können nur von Interfaces erben, aber nicht von anderen Klassen.
 *
 * WICHTIG: inline-Klassen können als Wrapper und als der darunterliegende Typ verwendet werden.
 *          aus diesem Grund darf man für inline-Klassen keine referentielle Gleichheit
 *          abprüfen.
 *
 *
 * Wann kann man das nutzen? Es hilft, die Signaturen von Funktionen sprechender zu machen.
 * Z.B. kann eine Funktion eine Dauer in Tagen benötigen. Dann wäre eine
 * inline-class Tage... sehr hilfreich, weil die Signatur dann sprechend ist.
 *
 *      fun verzoegere(days: Tage)
 *
 * ist besser als
 *
 *      fun verzoegere(days: Int)
 *
 * In diesem Zusammenhang könnte man natürlich auch mit einem Type-Alias arbeiten.
 *
 * Die folgende, sehr gut gemachte Seite, gibt noch mehr Hintergrund zu den inline-classes:
 *
 * <a href="https://typealias.com/guides/inline-classes-and-autoboxing/">link</a>
 *
 *
 * */


// Die Annotation ist lediglich für den JVM-Compiler,
// bei JavaScript bzw. nativem Code kann sie entfallen.
@JvmInline
value class StringHolder(val s: String) {
    init {
        require(s.length > 0) { }
    }

    val length: Int
        get() = s.length

    fun greet() {
        println("Hello, $s")
    }

    fun getContent(): String = s
}

fun main() {
    val strHolderA = StringHolder("Peter wars")
    val strHolderB = StringHolder("Peter wars")

    /**
     * Der folgende Code ist verboten, siehe oben wg. verbotener Identitätsprüfung
     * Es gibt sofort eine Compilermeldung
     *
     *       if(strHolderA===strHolderB) {
     *
     *       }
     */

    // das geht dagegen:
    if(strHolderA == strHolderB) {
        println("Die Inhalte der inline-classes sind gleich")
    }

    val strHolderInst: StringHolder = getValueClassInstance("Er wars")
    println(strHolderInst.getContent())
    println(strHolderInst.length)
    println(strHolderInst.greet())

}

fun getValueClassInstance(s: String): StringHolder {
    return StringHolder(s)
}

