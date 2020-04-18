package de.tiupe.dsl

/**
 * Operatoren zu überschreiben ist eine perfekte Möglichkeit, eine DSL
 * nach außen wie eine eigene Sprache aussehen zu lassen.
*/
operator fun String.times(n: Int)= repeat(n)

fun main() {
    /** Weil die Extension-Function mit operator markiert wurde, kann man jezt einfach
        auf dem String "mal 5" aufrufen.
        Das Ergebnis des Programms ist natürlich das Genialste:

            Peter ist der Beste kann man nicht oft genug lesen
            Peter ist der Beste kann man nicht oft genug lesen
            Peter ist der Beste kann man nicht oft genug lesen

     */
    println("\nPeter ist der Beste kann man nicht oft genug lesen" * 5)
}