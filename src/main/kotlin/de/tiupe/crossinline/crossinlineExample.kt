package de.tiupe.crossinline

/*
* Die Crossinline-Anweisung sorgt dafür, dass die Programmsteuerung nicht von einem übergebenen
* Lambda übernommen werden kann und zwar nur bei einer inline-Funktion.
* Mit der inline-Anweisung würde die return - Anweisung aus der multiplyByTwo
* in die main-Funktion gelangen und dort u. U. den Programmfluss steuern. Um
* dies zu unterbinden, kann man innerhalb der Inline-Funktion das Lambda mit crossinline
* markieren. Dadurch wird das übergebene return in der MultiplyByTwo zu einem Fehler.
* Hier habe ich es nur "auskommentiert" stehen, weil sonst der Code nicht mehr kompiliert.
* */

fun main() {
    multiplyByTwo(5) {
        println("Das Ergebnis ist: $it")
        /*
         * Diese return-Anweisung wird zum Fehler, wenn die crossinline Anweisung oben
         *  weggenommen wird.
         * */
        return
    }
}

inline fun multiplyByTwo(num: Int, /*crossinline*/ lambda:  (Int) -> Unit) : Int {
    val result = num * 2
    lambda.invoke(result)
    return result
}


