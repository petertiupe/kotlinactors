package de.tiupe.observerpattern

import java.util.*

// Observable ist die Klasse, die die Daten hält, die also observable (beobachtbar) sein soll.
// Die Observer werden dann informiert, wenn ein Wert sich geändert hat.

// Der folgende Artikel ist eine gute Einführung in die reaktive Progammierung:

//  https://blog.danlew.net/2017/07/27/an-introduction-to-functional-reactive-programming/

class ObservableData(intValue: Int) : Observable() {
    // So lange die Observable nicht informiert, passiert bei den Observern gar nichts...
    var intValue: Int = intValue
        set(intVal) {
            field = intVal
            setChanged()
            notifyObservers()
        }


}

fun main() {
    // marble diagramms show how to use this...
    val observer: Observer = object : Observer{
        override fun update(o: Observable?, arg: Any?) {
            when(o) {
                is ObservableData -> println("Update auf dem Observer wurde aufgerufen... " +
                        "der übergebene Wert war ${o.intValue}")
                else -> println("So war das nicht gedacht")
            }
        }
    }

    val observableData = ObservableData(intValue = 1)
    // So lange die Observable nicht informiert, passiert bei den Observern gar nichts...
    observableData.addObserver(observer)

    // Ab hier funktioniert der Mechanismus automatisch. Sobald der Int-Wert sich ändert, bekommt der Observer
    // das mit und kann auf den neuen Wert reagieren. Wichtig ist, dass der Observer bestimmt, ob er was macht oder
    // nicht, nicht der Observable Aus meiner Sicht gibt es an dieser Stelle allerdings noch die Lücke, dass der
    // Observable all seine Observer kennen muss. Dadurch ist eine enge Kopplung zwischen Observable und Observer
    // gegeben.
    observableData.intValue = 4
    observableData.intValue = 6

}

