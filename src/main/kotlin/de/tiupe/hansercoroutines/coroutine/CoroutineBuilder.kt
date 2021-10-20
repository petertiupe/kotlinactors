package de.tiupe.hansercoroutines.coroutine
import kotlinx.coroutines.*


fun main(){
    runBlocking {
        val a = GlobalScope.launch {
            println("Peter kann es")
            println("RunBlocking im äußeren Teil -> ${Thread.currentThread().name}")
        }
        while (!a.isCompleted) {

            println("I am waiting for the completion")
            delay(2000)
        }
        GlobalScope.launch {
            runBlocking {
                println("RunBlocking in GlobalScope.launch -> ${Thread.currentThread().name}")
            }
        }
        launch{
            println("launch einfach so.... -> ${Thread.currentThread().name}")
        }
    }

    System.exit(0)
}