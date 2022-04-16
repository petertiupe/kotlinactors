package de.tiupe.dsl

/**
 * Die entscheidende Idee bei dem Type-Safe Builder sind die beiden
 * Lambda-With-Receiver-Funktionen
 *
 * Die #typesafeint - Funktion erzeugt ein Wrapper-Objekt und ruft auf diesem dann gleich die
 * übergebene Funktion auf. Die Funktion steht außerhalb der Klasse. Der Trick dabei ist, dass
 * man in der Lambda with Receiver-Function einfach eine Lambda-Funktion zur Verfügung hat mit
 * dem Wrapper-Objekt als this.
 *
 * Damit kann man dann den Aufruf machen wie:
 *
 *  {
 *      intValue=...
 *  }
 *
 *  Man kann also in dem Lambda with Receiver auf die Properties der Klasse zugreifen.
 *
 *  In der Klasse hat man dann noch einmal eine Lambda-with-Receiver-Funktion. Diese fügt dann der Liste
 *  innerhalb des Objekts einen neuen Eintrag hinzu.
 *
 * */

// Rekursion schon in der Klassendefinition
// Die Klasse enthält eine Liste von Objekten, in der schon wieder IntWrapper stecken
class IntWrapper(var intValue: Int = 0, var intTree: List<IntWrapper> = emptyList()){
    fun addToIntList(addFunkt: IntWrapper.() -> Unit) {
        // also bekommt eine Funktion übergeben, die eine Lambda-Funktion auf dem Objekt
        // aufruft.
        /* Hier ein Beispiel aus der Doku
        *
        * Die Arbeitsweise der Scope-Functions kann man sehr gut in dem Paket
        * scopefunctions analysieren
        * numbers
            .also { println("The list elements before adding new one: $it") }
            .add("four").
            .also { println("The list elements after adding new one: $it")}
        *
        *
        * */
        IntWrapper().also { newIntWrapper ->
            newIntWrapper.addFunkt()
            intTree+=newIntWrapper
        }
    }
    // Die Funktion geht über die Kinder und addiert sie rekursiv auf
    // Die Fold-Funktion entspricht der foldLeft aus scala.
    fun getSum(): Int {
        return intTree.fold(intValue) { acc: Int, intWrapper: IntWrapper ->
            acc + intWrapper.getSum()
        }
    }
}

fun typesafeint(initFukt: IntWrapper.() -> Unit): IntWrapper{
    // Es wird ein neues Objekt erzeugt und auf dem Objekt die initFunkt aufgerufen.
    // zurück kommt dann das Objekt selbst.
    return IntWrapper().apply(initFukt)
}

fun main(){
    // einfachster Aufruf
    val simple = typesafeint {
        intValue = 3
        // zeigt die Arbeitsweise von apply, der neue Wert ist 4
        apply { intValue = 4 }
    }

    println(simple.intValue.toString())

    val a =typesafeint {
        // Lambda with Receiver stellt ein this-objekt zur Verfügung, daher kann
        // man hier einfach auf die Eigenschaften und auch auf die Funktionen zugreifen.
        intValue = 3
        addToIntList {
            intValue = 5
        }
        addToIntList {
            intValue =7
            addToIntList {
                intValue = 1
            }
        }
    }
    println(a.getSum())
}