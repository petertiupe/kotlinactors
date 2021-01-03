package de.tiupe.asynchnachbuch

// Anzahl der Integrations-Schritte
const val MAXSTEPS = 10_000_000

fun integrateNumeric(start: Double, end:Double, func: (Double) -> Double): Double {
    val steps = MAXSTEPS
    var result = 0.0

    // Integrationsintervall ist 0
    if(start == end) return 0.0

    val delta = (end - start) / (steps - 1)

    for(i in 0 until steps) {
        val x = start + delta * i
        result += func(x)
    }
    return result * delta
}

fun main(){
    // einfache Funktion f(x) = x^2
    // Integral = 1/3 x^3
    // Intervall 1 bis 3 also erwarte ich das Ergebnis
    // 1/3 * 27 - 1/3 * 1 = 8 2/3
    // geliefert wird beim ersten Lauf 8.666667666667417

    val result = integrateNumeric(1.0, 3.0, {d  -> d * d})
    println("Das Ergebnis ist: $result")
}