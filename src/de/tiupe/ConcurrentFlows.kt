package de.tiupe

// Für die Time-Funktion benötigt
import kotlin.system.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

// Die Funktion erzeugt einen Flow von Zahlen von 1 bis 10 mit
// der entsprechenden Verzögerung.
val ints: Flow<Int> = flow {
    for (i in 1..10) {
        delay(1000)
        emit(i)
    }
}

// Dies ist eine Extension-Function, die einen Flow zurückgibt
// und mit Channels arbeitet

// Der Kotlin coroutine-Produce-Builder kombiniert den Aufbau einer neuen
// Coroutine und das Erzeugen eines Channels. Anschließend werden die
// Funktionspaare auf der Konsumer-Seite kombiniert.
// Der coroutineScope sorgt dafür, dass die Concurrency strukturiert abläuft.

// Ganz werde ich den Code wohl erst verstehen, wenn ich mir auch die Channel angesehen habe.

@ExperimentalCoroutinesApi
fun <T> Flow<T>.eigeneAblaufumgebung(size: Int = 0): Flow<T> = flow {
    coroutineScope {
        val channel = produce(capacity = size) {
            collect { send(it) }
        }
        channel.consumeEach { emit(it) }
    }
}

suspend fun main() {
    val time = measureTimeMillis {
        // Auf dem Flow der Integerzahlen wird die selbstgeschriebene
        // buffer-Funktion aufgerufen, die ebenfalls einen Flow erzeugt.
        // Von diesem Flow wird das Ergebnis eingesammelt. Daher
        // der Aufruf von collect. Collect sammelt in diesem Fall
        // das Ergebnis des erzeugten Kanals, der in einem eigenen,
        // via coroutineScope erzeugten Thread läuft.
        ints.eigeneAblaufumgebung().collect {
            delay(1000)
            println(it)
        }
    }
    println("Collected in $time ms")
}