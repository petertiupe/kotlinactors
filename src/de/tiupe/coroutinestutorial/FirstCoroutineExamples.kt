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
    /* exampleBlocking()
    exampleBlockingDispatcher()
    exampleLaunchGlobal()
    exampleLaunchCoroutineScope()*/
    exampleWithContext()

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
/*
Anhand der Ausgabe  erkennt man, dass launch einen eigenen Thread
beginnt. Der Launch-Aufruf ist nicht mehr blockend für den ihn umgebenden Code.
one - from thread main
three - from thread main
two - from thread DefaultDispatcher-worker-1

In der Dokumentation findet man dazu folgenden Satz:
        Launches a new coroutine without blocking the current thread
        and returns a reference to the coroutine as a [Job].
        The coroutine is cancelled when the resulting job is [cancelled][Job.cancel].
*/
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

/*
* In dieser Funktion geht es um ein Beispiel in dem gezeigt wird, wie man einen eigenen
* Thread-Pool aufbauen kann. Der Thread-Pool selbst stammt aus dem Paket
*
*       package java.util.concurrent
*
* asCoroutineDispatcher ist eine Extension-Function auf dem ExecutorService und stellt
*                       diesen lediglich als CoroutineDispatcher zur Verfügung. Der Dispatcher
*                       selbst macht das was sein Name sagt:
*                       "It  determines what thread or threads the corresponding coroutine
*                        uses for its execution"
*
* Als Ergebnis liefert diese Funktion:
*
*       one - from thread main
*       three - from thread main
*       two - from thread pool-1-thread-1
*
* Die Ausgabe ist in diesem Beispiel nur zweitrangig. Da der Aufruf von
* printDelayed durch das launch nicht blockend geschieht, läuft das Programm durch,
* und wartet, bis die printDelayed Funktion auch gelaufen ist.
* */
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

/*
* In dieser Funktion wird vorgestellt, wie man mit drei Aufgaben parallel
* abarbeiten lassen kann und anschließend die Ergebnisse zusammenfasst.
*
* Wieder gibt man dem withContext eine Funktion mit, die als Rückgabewert einen
* Type T bekommt, in diesem Fall ein Int
* Zur Erläuterung hier die Definition von withContext:
*
*            public suspend fun <T> withContext(
*               context: CoroutineContext,
*               block: suspend CoroutineScope.() -> T
*            ): T
*
* Dem CoroutineScope wird mit einer Function-Extension ein Code-Block übergeben,
* der einen Typ T zurückliefert. Genau diesen Typen wollen wir als Rückgabewert
* haben, in diesem Fall also das Ergebnis von
*
*   calculateHardThings
*
* */
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
