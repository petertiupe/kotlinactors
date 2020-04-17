package de.tiupe.hotandcold

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val numbers = produceNumbers() // produces integers from 1 and on
    val squares = square(numbers) // squares integers
    repeat(10) {
        println(squares.receive()) // print first five
    }
    println("Fertig!") // we are done
    coroutineContext.cancelChildren() // cancel children coroutines
}

// Auch hier wie in der nächsten Methode der produce-Aufruf (s.u.)
fun CoroutineScope.produceNumbers() = produce <Int> {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}

// Hier wird wieder das Producer-Pattern benutzt, das Ergebnis ist also ein Receive-Channel,
// der den sendenden Channel intern hält.
// Den Pipeline-Charakter erkennt man daran, dass ein ReceiveChannel als Argument eingeht und auch
// ein ReceiveChannel als Ergebnis geliefert wird. So lässt sich eine ordentliche call-Chain aufbauen.
fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}