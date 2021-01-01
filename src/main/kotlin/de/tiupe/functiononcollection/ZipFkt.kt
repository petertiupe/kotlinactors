package de.tiupe.functiononcollection

/**
 * Die zip-Funktion führt zwei Listen zusammen, solange beide Listen
 * Elemente besitzen. Ist wie in dem hier gezeigten Beispiel die eine
 * Liste kürzer als die andere, werden die übrigen Elemente der längeren
 * Liste ausgelassen.
 *
 * Das Ergebnis des Programms ist das folgende:
 *
 *          Das Ergebnis der zip-Funktion ist:
 *          1 ist mit a verknüpft, zip hats vollbracht
 *          2 ist mit b verknüpft, zip hats vollbracht
 *          3 ist mit c verknüpft, zip hats vollbracht
 *          4 ist mit d verknüpft, zip hats vollbracht
 *          5 ist mit e verknüpft, zip hats vollbracht
 *          Da die zweite Liste zuende war ist jetzt auch die zip-Funktion am Ende :-)
 *
 * */
fun main(){
    val listA: List<Int> = listOf(1,2,3,4,5,6,7,8,9)
    val listB: List<String> = listOf("a", "b", "c", "d", "e" )

    val zipped: List<Pair<Int, String>> = listA.zip(listB)

    println("Das Ergebnis der zip-Funktion ist:")
    for ((a,b) in zipped){
        println("$a ist mit $b verknüpft, zip hats vollbracht")
    }

    println("Da die zweite Liste zuende war ist jetzt auch die zip-Funktion am Ende :-) ")



}