package de.tiupe

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val deferred1 = GlobalScope.async {
        double(1)
    }
    val deferred2 = GlobalScope.async {
        double(2)
    }
    val job: Job = GlobalScope.launch {
        val sum = deferred1.await() + deferred2.await()
        println(sum)
    }
   job.join()
}

suspend fun double(x: Int): Int {
    delay(1000)
    return x * 2
}