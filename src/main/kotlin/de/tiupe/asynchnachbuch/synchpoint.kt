package de.tiupe

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/*
* In diesem Beispiel geht es um die Synchronisation. Eine globale Variable soll von mehreren Coroutinen
* verwendet werden.
* */

fun main() {
    val soGehtsNicht = soGehtsNicht()
    println("So gehts nicht liefert bei 250.000 Zugriffen nur $soGehtsNicht Zugriffe, der Zähler funktioniert nicht!")
    println("Es fehlen ${250_000 - soGehtsNicht} Zugriffe!!!")
    println()

    val soGehtsMitEinemThread = soGehtsMitNurEinemThread()
    println("So gehts bei 250.000 Zugriffen werden bei einem Thread auch $soGehtsMitEinemThread Zugriffe durchgeführt, der Zähler funktioniert!!!")
    println("Es fehlen ${250_000 - soGehtsMitEinemThread} Zugriffe!!!")
    println()

    val soGehtsMitMutex = soGehtsMitMutex()
    println("So gehts bei 250.000 Zugriffen werden mit Mutex auch $soGehtsMitEinemThread Zugriffe durchgeführt, der Zähler funktioniert!!!")
    println("Es fehlen ${250_000 - soGehtsMitEinemThread} Zugriffe!!!")

}

fun soGehtsNicht(): Int {
    var sharedCounter: Int = 0
    runBlocking {
        for(i in 1..250_000) {
            launch(Dispatchers.Default) {
                delay((0..100L).random())
                sharedCounter += 1
            }
        }
    }
    return sharedCounter
}

fun soGehtsMitNurEinemThread(): Int {
    var sharedCounter: Int = 0
    val single = newSingleThreadContext("singleThreadContext")
    runBlocking {
        for(i in 1..250_000) {
            launch(single) {
                delay((0..100L).random())
                sharedCounter += 1
            }
        }
    }
    return sharedCounter
}

fun soGehtsMitMutex(): Int {
    var sharedCounter: Int = 0
    val mutex = Mutex()
    runBlocking {
        for(i in 1..250_000) {
            launch(Dispatchers.Default) {
                delay((0..100L).random())
                // Hier steht der mutex-Lock in einem Lambda
                mutex.withLock {
                    sharedCounter += 1
                }
            }
        }
    }
    return sharedCounter
}