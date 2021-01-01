package de.tiupe.asynchnachbuch

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


fun main() = runBlocking {
  /*
  * yield sorgt hier dafür, dass auch der Child-Job noch anlaufen kann, weil
  * nur mit der Yield-Anweisung der Thread auch für den Child-Prozess Resourcen
  * freigibt.
  *
  * yield gibt also den Thread frei, wenn zwei Funktionen sich sonst gegenseitig
  * blocken würden.
  * Hier würde die äußere Launch-Anweisung den Thread beanspruchen und bis zum
  * Ende durchlaufen.
  *
  * */
  val job = launch {
        val child = launch {
            try {
                println("Child is running")
                delay(Long.MAX_VALUE)
            } finally {
                println("Child is cancelled")
            }
        }

        yield() // without this, child job doesn't get executed
        println("Cancelling child")
        child.cancel()
        child.join()
        yield()
        println("Parent is not cancelled")
    }
    println("Parent is running")
    job.join()
    }
