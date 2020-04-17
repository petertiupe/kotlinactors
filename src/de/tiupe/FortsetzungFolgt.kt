package de.tiupe
import kotlinx.coroutines.*
// Beispiel für einen Alias
// import kotlinx.coroutines.CoroutineScope as CoroutineScope1


fun main() = runBlocking<Unit> {
    val job: Job = GlobalScope.launch {
        greet("Pia\n")
    }
    print("Hi ")
    // JVM am Leben halten
    job.join()
    println("Ende.")
}

suspend fun greet(a: String){
    delay(1000L)
    print(a)
}