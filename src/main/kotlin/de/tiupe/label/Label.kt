package de.tiupe.label
/**
 * Das Programm zeigt lediglich, wie die Label gesetzt und verwendet werden.
 * Das Programm selbst ist ohne Sinn und gibt die folgenden Zeilen aus:
 *
 * (1, 1)
 * (1, 2)
 * (1, 3)
 * (1, 4)
 * (1, 5)
 * (2, 1)
 * (2, 2)
 * (2, 3)
 * (2, 4)
 * (2, 5)
 *
 * */
fun main(){
    peter@for(i in 1..100){
        tina@for(j in 1..100){
            if(i > 2) break@tina
            if(j > 5) continue@peter
            println("($i, $j)")
        }
    }
}