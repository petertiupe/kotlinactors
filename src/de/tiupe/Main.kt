package de.tiupe

import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope as CoroutineScope1

fun callMe(a: Int): Int?{
    if(a == 5){
        return 5
    } else {
        return null
    }
}
/*
fun main(args: Array<String>) {
    var name: String? = "Peter"
    name = null

    println(name?.length) // liefert null
    if(name != null) {
        println(name.toString())
    } else {
        println("Dar war ne null versteckt....")
    } // liefert den else-Zweig...
    println(name!!.length) // liefert eine Nullpointer-Exception


}
*/
/*
fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1500L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1500L) // non-blocking delay for 1 second (default time unit is ms)
        println("World2!") // print after delay
    }
    GlobalScope.launch {
        delay(1500L)
        println("World3!")
    }

    println("Hello,") // main thread continues while coroutine is delayed
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}
*/
/*
fun main() = runBlocking<Unit> { // start main coroutine
    launch { // launch a new coroutine in background and continue
        delay(1500L)
        println("World!")
    }
    launch { // launch a new coroutine in background and continue
        delay(1500L) // non-blocking delay for 1 second (default time unit is ms)
        println("World2!") // print after delay
    }
    launch {
        delay(1500L)
        println("World3!")
    }
    println("Hello,") // main coroutine continues here immediately
}
*/

/*
fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    // Wenn dieser Teil auskommentiert wird, schreibt das Progamm nur den einen Satz raus
    // Coroutine scope is over.

    coroutineScope {
        firstSuspendingFunction()
    }

    println("Coroutine scope is over") // This line is not printed until the nested launch completes
}
*/
suspend fun firstSuspendingFunction(){
    delay(500L)
    println("Task from nested launch")
    // Eine Suspending Function kann selbst wieder suspending Functions benutzen.
    delay(100L)
    println("Task from coroutine scope") // This line will be printed before the nested launch
    }

/*
fun main() = runBlocking<Unit> {
    val scope0 = this
    // scope0 is the top-level coroutine scope.
    scope0.launch {
        val scope1 = this
        // scope1 inherits its context from scope0. It replaces the Job field
        // with its own job, which is a child of the job in scope0.
        // It retains the Dispatcher field so the launched coroutine uses
        // the dispatcher created by runBlocking.
        delay(2000)
        println("Tina")
        scope1.launch {
            val scope2 = this
            // scope2 inherits from scope1
            delay(1000)
            println("Peter")
        }
        delay(3000)
        println("Inken")
    }
    println("Ende und aus.")

}*/

fun main() = runBlocking {

    repeat(100_000) { // launch a lot of coroutines
        launch {
            delay(1000L)
            print(".")
        }

    }
    println("gleich gehts los")
}