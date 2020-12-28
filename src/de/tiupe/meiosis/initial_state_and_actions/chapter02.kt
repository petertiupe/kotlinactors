package de.tiupe.meiosis.initial_state_and_actions

/*
*
* Meiosis ist ein Framework für das Managen des Status einer Anwendung.
* Die Idee dahinter ist es, ein Top-Level-Objekt zu haben, das den Status der Anwendung
* enthält. Dieser Status wird möglichst einfach zu ändern sein.
*
* */

/*
* Dies ist das Objekt, das den Status enthält.
* State-Objekte bildet man in Kotlin i.d.R. durch data-classes ab, also
* fange auch ich so an.
*  */

data class ApplicationState(val state: Int)


/* Der Status soll durch Actions geändert werden. */
fun ApplicationState.increment(): ApplicationState {
    return ApplicationState(this.state + 1)
}

fun main(){
    val firstState = ApplicationState(0)
    val secondState = firstState.increment()
    println("Der Status hat sich geändert: ${secondState.state}")
    val thirdState = secondState.increment()
    println("So kann es weitergehen: ${thirdState.state}")
}

/*
* Was habe ich bis hierhin?
*
* 1. einen initialen Status
* 2. eine Art Handler, die den "alten" Status auf einen "neuen" Satus abbildet. Es wird (funktional) bei jedem
*    Aufruf von increment ein neues Objekt erzeugt.
*
* Es fehlt noch etwas, dass die Reihenfolge der Änderungen festlegt. Hier muss man noch davon ausgehen, dass
* immer der Handler auf dem "richtigen" Objekt aufgerufen wird.
*
* Die Lösung an dieser Stelle könnten Streams sein. Kotlin hat sich an dieser Stelle entschieden, nicht von
* Streams zu sprechen, sondern um sich von Java zu unterscheiden, von Sequences.
*
* */