package de.tiupe.coroutinestutorial

import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
/*
* In dieser Klasse werden mehere Beispiele gezeigt, die zur Einführung
* in die Begriffe Scope, Context und die Builder dienen.
* Am verständlichsten ist es, wenn in der main-Methode nur jeweils
* eine Methode aufgerufen und ihr Verhalten untersucht wird.
* */
fun main(args: Array<String>) {
    /* exampleBlocking() */
    exampleBlockingDispatcher()
    /*
    exampleLaunchGlobal()
    exampleLaunchCoroutineScope()
    exampleWithContext()
    */
}


suspend fun calculateHardThings(startNum: Int): Int {
    delay(1000)
    return startNum * 10
}

/*
* Die Funktion zeigt das Verhalten von runBlocking als blockende
* Ausführung von suspending Functions. Die Funktionen, die aus
* runBlocking heraus aufgerufen werden, werden nacheinander ausgeführt und es
* wird jeweils auf die Ausführung der suspending-Function gewartet
* */
fun exampleBlocking() = runBlocking {
    println("one")
    printlnDelayed("two")
    println("three")
}

// Running on another thread but still blocking the main thread
/*
* Diese Funktion ist ein Beispiel dafür, dass runBlocking mit einem übergebenen
* Dispatcher in einem anderen Thread läuft, jedoch immer noch blockend ist.
* Die Ausgabe ist:
*       one - from thread DefaultDispatcher-worker-1
*       two - from thread DefaultDispatcher-worker-1
*       three - from thread main
* */
fun exampleBlockingDispatcher(){
    runBlocking(Dispatchers.Default) {
        println("one - from thread ${Thread.currentThread().name}")
        printlnDelayed("two - from thread ${Thread.currentThread().name}")
    }
    // Diese Zeile wird außerhalb von runBlocking aufgerufen, um zu zeigen,
    // dass die Zeile im blocked main thread läuft
    // Auch diese Zeile läuft erst, wenn der runBlocking-Block durchgelaufen ist.
    println("three - from thread ${Thread.currentThread().name}")
}

fun exampleLaunchGlobal() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    GlobalScope.launch {
        printlnDelayed("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")
    delay(3000)
}

fun exampleLaunchGlobalWaiting() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    val job = GlobalScope.launch {
        printlnDelayed("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")
    job.join()
}

fun exampleLaunchCoroutineScope() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    val customDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

    launch(customDispatcher) {
        printlnDelayed("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")

    (customDispatcher.executor as ExecutorService).shutdown()
}

fun exampleAsyncAwait() = runBlocking {
    val startTime = System.currentTimeMillis()

    val deferred1 = async { calculateHardThings(10) }
    val deferred2 = async { calculateHardThings(20) }
    val deferred3 = async { calculateHardThings(30) }

    val sum = deferred1.await() + deferred2.await() + deferred3.await()
    println("async/await result = $sum")

    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")
}

fun exampleWithContext() = runBlocking {
    val startTime = System.currentTimeMillis()

    val result1 = withContext(Dispatchers.Default) { calculateHardThings(10) }
    val result2 = withContext(Dispatchers.Default) { calculateHardThings(20) }
    val result3 = withContext(Dispatchers.Default) { calculateHardThings(30) }

    val sum = result1 + result2 + result3
    println("async/await result = $sum")

    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")
}

suspend fun printlnDelayed(message: String) {
    // Complex calculation
    delay(1000)
    println(message)
}
