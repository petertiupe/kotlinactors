package de.tiupe.hotandcold

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.coroutines.CoroutineContext

fun main() = runBlocking {
    // Endlosstream mit allen Zahlen ab der Zwei
    var cur = numbersFrom(2)
    repeat(100) {
        val a: CoroutineContext = coroutineContext

        // Zahl aus dem Endlos-Stream liefern lassen und zwar hier noch ohne Filter
        // also noch keine Primzahlen
        val prime: Int = cur.receive()
        println(prime)
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}

// Hier wird ein endlos-Stream von Integer-Werten erzeugt.(Siehe auch PipelineExample in diese Package)
fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // infinite stream of integers from start
}

// Hier kommt das Sieb des Eratosthenes
fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) if (x % prime != 0) send(x)
}