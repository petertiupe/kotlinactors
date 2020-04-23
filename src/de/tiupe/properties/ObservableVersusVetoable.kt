package de.tiupe.properties

import kotlin.properties.Delegates

class ObservableAndVetoable() {

    var observableVar: String by Delegates.observable("initialer Wert der observableVar") {
        _, oldVal, newVal -> println("Mir wurde als observable ein neuer Wert zugewiesen: ${newVal}")
    }

    var vetoableVar: String by Delegates.vetoable("initialer Wert der vetoableVar") {
         _ , oldVal, newVal ->
        if (newVal != "Peter ist der Beste"){
            println("Dich nehm ich nicht als Wert!!!!")
            false
        } else {
            println("Der Inhalt gefällt mir")
            true
        }
    }

}

/**
 * Das Programm liefert wie zu hoffen war :-) die folgende Ausgabe:
 *
 * Test der observable Variablen
 * =============================
 * Zunächst der initiale Wert: initialer Wert der observableVar
 * Mir wurde als observable ein neuer Wert zugewiesen: neuer Wert der observableVar
 * Nach dem Setzen des Wertes mit dem Zugriffsausdruck davor: neuer Wert der observableVar
 *
 *
 * Test der vetoable Variablen
 * ===========================
 * Zunächst der initiale Wert: initialer Wert der vetoableVar
 * Dich nehm ich nicht als Wert!!!!
 * Nach dem Setzen des Wertes mit dem Zugriffsausdruck das Veto hat hoffentlich zugeschlagen: initialer Wert der vetoableVar
 * Der Inhalt gefällt mir
 * Nach dem Setzen des Wertes mit dem Zugriffsausdruck ohne Veto: Peter ist der Beste
 *
 * */
fun main(){
    val myObj = ObservableAndVetoable()
    println("Test der observable Variablen")
    println("=============================")
    println("Zunächst der initiale Wert: ${myObj.observableVar}")
    myObj.observableVar = "neuer Wert der observableVar"
    println("Nach dem Setzen des Wertes mit dem Zugriffsausdruck davor: ${myObj.observableVar}")

    println("\n\nTest der vetoable Variablen")
    println("===========================")
    println("Zunächst der initiale Wert: ${myObj.vetoableVar}")
    myObj.vetoableVar = "Peter ist doof"
    println("Nach dem Setzen des Wertes mit dem Zugriffsausdruck das Veto hat hoffentlich zugeschlagen: ${myObj.vetoableVar}")
    myObj.vetoableVar = "Peter ist der Beste"
    println("Nach dem Setzen des Wertes mit dem Zugriffsausdruck ohne Veto: ${myObj.vetoableVar}")


}

