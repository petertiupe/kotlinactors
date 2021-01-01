package de.tiupe.lambdawithreceiver

class HTML() {
    fun body(a: String){
        println("Dies ist der HTML-Body mit dem Inhalt $a")
    }
}

// Hier beginnt der Typesafe-Builder
fun html(init: HTML.() -> Int): HTML {
    val html = HTML() // hier wird das Receiver-Objekt erzeugt

    // auf dem Receiver-Objekt kann man jetzt die übergebene Funktion vom Typ
    // Lambda with Receiver aufrufen. Lässt man den Aufruf weg, wird der Block,
    // der hier übergeben wird, einfach nicht ausgeführt und es wird ein HTML-Objekt
    // zurückgegeben.
    html.init()
    return html
}

/* In den aufrufenden Klassen hat man damit eine sehr schöne Syntax zur Verfügung:
 * Entscheidend ist hier die Syntax für den Nutzer der obigen Funktionen, nicht die
 * Ausgabe, der Vollständigkeit halber:
 *
 *      Dies ist der HTML-Body
 *
 * wird von dem Programm ausgegeben.
 */
fun main(){
    html {
        // das Receiver-Object ist ein Objekt vom Typ HTML, d.h. hier kann man dessen
        // Funktionen aufrufen. Die geschweiften Klammern sind dann nichts anderes, als
        // ein Lambda-Aufruf als letzem Parameter in der Funktion html
        body("Peter wars") // der Code hinter diese Funktion wird letztendlich in dem init-Parameter
        6
               // zur Verfügung gestellt.
    }

    // Um die Schreibweise klar zu machen, hier noch einmal als expliziter Funktionsaufruf:
    // Durch das Lambda with Receiver-Objekt steht die this-Referenz in dem Aufruf zur Verfügung.
    val lamdaWithReceiver: HTML.() -> Int = {
        // Hier hab ich mich erst mit den Rückgabetypen ein wenig schwer getan.
        // Der Rückgabetyp für das Function-Literal HTML.() -> Unit
        // bedeutet ja nichts anderes als rufe auf dem HTML-Objekt eine Funktion auf, die als
        // Rückgabetyp Unit hat. Welche Parameter die Funktion benötigt, die in dem Lambda with receiver
        // aufgerufen wird, ist erst einmal egal.

        this.body("Peter wars")
        6

    }
    html(init = lamdaWithReceiver)
}