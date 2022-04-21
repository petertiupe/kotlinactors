package de.tiupe.dsl

/**
 * In der Datei ExtensionFunctionDSL steht ein Beispiel, wie man einen Typesafe-Builder
 * mit Kotlin schreiben kann. In dem Beispiel sind die Klassen allerdings mit var statt mit
 * val Variablen definiert.
 *
 * Hier nun dasselbe Beispiel noch einmal mit vals
 *
 * Der einzige Trick dabei ist, dass man ein Zwischenobjekt nutzt, in dem die Werte in der build-Fkt
 * dann in das unveränderliche Objekt übertragen werden.  Hier nutze ich als Zielobjekt eine data class.
 *
 *
 * */



data class IntWrapperWithVal(val intValue: Int = 0, val intTree: List<IntWrapperWithVal> = emptyList()) {
    /* fun addToIntList(addFunkt: IntWrapperWithVal.() -> Unit) {
        IntWrapperWithVal().also { newIntWrapperWithVal: IntWrapperWithVal ->
            newIntWrapperWithVal.addFunkt()
            intTree+=newIntWrapperWithVal
        }
    }
    */
    // Die Funktion geht über die Kinder und addiert sie rekursiv auf
    // Die Fold-Funktion entspricht der foldLeft aus scala.
    fun getSum(): Int {
        return intTree.fold(intValue) { acc: Int, intWrapper: IntWrapperWithVal ->
            acc + intWrapper.getSum()
        }
    }
}

/* Um bei der Ziel-Klasse mit vals arbeiten zu können, arbeitet die folgende Builder-Klasse
*  mit vars und erzeugt das gewünschte Zielobjekt
* */
class IntWrapperBuilder {
    var intValue: Int = 0
    var intTree = mutableListOf<IntWrapperWithVal>()

    fun addToIntList(initFkt: IntWrapperBuilder.() -> Unit) {
        IntWrapperBuilder().also { newWrapperBuilder: IntWrapperBuilder ->
            newWrapperBuilder.initFkt()
            intTree.add(newWrapperBuilder.build())
        }

    }

    fun build(): IntWrapperWithVal {
        return IntWrapperWithVal(intValue, intTree)
    }
}


fun intWrapperWithVal(initFunkt: IntWrapperBuilder.() -> Unit ): IntWrapperWithVal {
    val a: IntWrapperBuilder = IntWrapperBuilder().apply(initFunkt)
    val b: IntWrapperWithVal = a.build()
    return b
}

fun main() {
    val intWrapperSimple = intWrapperWithVal {
        intValue = 2
    }

    val intWrapper: IntWrapperWithVal = intWrapperWithVal {
        intValue = 5
        addToIntList {
            intValue = 6
        }
        addToIntList {
            intValue = 7
            addToIntList {
                intValue = 1
                addToIntList {
                    intValue = 2
                    addToIntList {
                        intValue = 3
                    }
                }
            }
        }
        addToIntList {
            intValue = 122
        }
    }

    println("Summe: ${intWrapper.getSum()}")

}