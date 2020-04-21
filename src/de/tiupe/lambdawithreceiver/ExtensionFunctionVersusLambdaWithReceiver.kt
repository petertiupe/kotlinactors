package de.tiupe.lambdawithreceiver

/**
 * In dieser Datei findet ein Syntaxvergleich zwischen einem Lambda with Receiver und einer
 * Extension-Function statt. Beide Arten von Erweiterung verfolgen das Konzept, einer Klasse/ Funktion
 * nach außen hin Funktionalität zur Verfügung zu stellen, die so aussieht als wäre sie in der Klasse
 * definiert.
 *
 * Aus der Dokumentation:
 *
 *      Function types can optionally have an additional receiver type,
 *      which is specified before a dot in the notation:
 *
 *          the type A.(B) -> C
 *
 *      represents functions that can be called on a receiver object of
 *      A with a parameter of B and return a value of C.
 *      Function literals with receiver are often used along with these types.
 *
 * Wenn man sich das einmal klar macht, ist die Notation sehr logisch:
 *
 * Beispiel:
 *
 *      (Int, Int) -> Int
 *
 * ist der Typ einer Funktion die zwei Int-Werte entgegen nimmt und einen Int-Wert zurück liefert.
 *
 *      Int.(Int, Int) -> Int
 *
 * ist der Typ einer Funktion, die zwei Int-Werte entgegennimmt und einen Int-Wert zurück liefert,
 * also alles wie oben, nur dass sie auf einem Int-Wert aufgerufen werden kann.
 *
 */

// Funktion, die ein Lambda with Receiver zurückliefert
fun addThreeNumbers(): Int.(Int, Int) -> Int {
    val summFkt = fun Int.(x:Int, y: Int): Int{
        return (this + x + y)
    }
    return summFkt
}

// Lambda with Receiver oder auch Function-Literal
// Das ist meine größte Erkenntnis, dass man die Function with Receiver
// Notation benötigt, um ein Function-Literal also ein Objekt vom Typ einer Funktion
// zu erzeugen, während die Extension-Function eben eine Funktion ist.
val addiereZweiZahlen: Int.(Int, Int) -> Int = { a: Int, b: Int ->
    this + a + b
}

// Als Vorteil für den Function with Receiver Type wird in der Dokumentation geannnt, dass man
// mit dieser Syntax das Member des Receivers im Body der Funktion zur Verfügung hat.
// Hier kann man also in der Notation bereits die Variablen Namen vergeben. Syntaktisch ergibt
// sich dasselbe wie oben, wie man auch unten in dem Funktionsaufruf sieht.
val sumThree: Int.(Int, Int) -> Int = fun Int.(other: Int, thirdInt): Int {
    return this + other + thirdInt
}

// Extension-Function:
fun Int.addTwoNumbers(a: Int, b: Int): Int {
    return this + a + b
}

fun main(){
    // Der Aufruf in der folgenden Form ist natürlich noch nicht das, was man gerne möchte.
    // Das Ziel ist ein Aufruf wie 5.addiereZweiZahlen(6,7) und als Ergebnis möchte man 18
    val summe = addThreeNumbers().invoke(1,2,3)
    println("Die Summe ist $summe")

    // Der Trick besteht darin, nicht eine Funktion zu definieren, obwohl das wie man oben sieht
    // auch geht, sondern einen val zu definieren. Die Fuktion addThreeNumbers liefert eine Funktion
    // zurück, gefordert ist an der Stelle jedoch ein Lambda und dass kann man nur einer Variablen
    // zuordnen, sonst wäre es kein Lambda, sondern eine Funktion, etwas verwirrend, aber so ist
    // in Kotlin die Definition.

    val additionsErgebnis = 5.addiereZweiZahlen(6, 7)
    println("Die Addition als Lambda with Receiver ergibt: $additionsErgebnis")

    val sumOfThreeCall = 5.sumThree(6, 7)
    println("Die weitere Syntax ergibt: $sumOfThreeCall")

    // Und hier jetzt als Extension-Function:
    val extensionResult = 5.addTwoNumbers(6, 7)
    println("Als Extension Function erhält man dasselbe Ergebnis: $extensionResult")



    /* Wie man sieht, ist sowohl das Ergebnis der letzten beiden Aufrufe als auch die Syntax beim
    *  Aufruf dieselbe. Diese Erkenntnis liefert auch die Dokumentation:
    *
    *       This behavior (ein this-Objekt zur Verfügung zu haben Anmerkung von mir) is similar
    *       to extension functions, which also allow you to access the
    *       members of the receiver object inside the body of the function.
    *
    * */

    /* Merken muss ich mir also:
    *
    *       Lambda with Receiver ist ein Function Literal
    *       während eine Extension-Function eben eine Funktion ist....
    *  */


}