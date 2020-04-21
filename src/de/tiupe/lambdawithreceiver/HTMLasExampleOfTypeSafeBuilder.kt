package de.tiupe.lambdawithreceiver

class HTML() {
    fun body(){
        println("Dies ist der HTML-Body")
    }
}

// Hier beginnt der Typesafe-Builder
fun html(init: HTML.() -> Unit): HTML{
    val html = HTML() // hier wird das Receiver-Objekt erzeugt

    // auf dem Receiver-Objekt kann man jetzt die übergebene Funktion vom Typ
    // Lambda with Receiver aufrufen
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
        body()
    }
}