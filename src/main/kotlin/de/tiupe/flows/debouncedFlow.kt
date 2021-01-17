package de.tiupe.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

val flowP: Flow<Int> = flow {

    emit(1)
    delay(1010)
    emit(2)
    delay(80)
    emit(3)
    delay(5000)
    emit(4)
    delay(900)
    emit(6)
    delay(110)
    emit(7)
    delay(500)
    emit(8)
    delay(700)
    emit(9)
    delay(5)
    emit(10)
}.debounce(1000)

fun main() {
    /* Ein debounced-Flow ist ein geprellter Flow, also einer, der die Werte nur dann auf die Reise
    *  schickt, wenn ein bestimmtes Zeitintervall nach dem versenden des Wert vergangen ist.
    *
    * Die Ausgabe des obigen Programms ist
    *
    *        1  -->     danach 1010 Millisekungen Pause
    *        3  -->     danach 5000 Millisekunden Pause
    *       10  -->     danach keine Pause, aber der letzte Wert wird immer zurückgegeben.
    *
    *       eine Kummulation der Werte findet offensichtlich nicht statt
    * * */
    runBlocking{
        flowP.collect {fl ->
            println(fl.toString())
        }
    }


}
