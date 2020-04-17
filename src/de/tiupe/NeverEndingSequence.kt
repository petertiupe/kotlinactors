package de.tiupe
// import kotlinx.coroutines.*

/*
*  Scala-Streams in Kotlin...
*  Dieses Beispiel stammt aus dem Artikel Fortsetzung folgt
*  und kann ohne coroutinen aufgerufen werden. Sie dienen lt.
*  dem Autor lediglich zur Abbildung der Sequence, d.h. man merkt
*  wie hier zu sehen im eigenen Programm gar nichts von den
*  Coroutinen.
*  */
fun main(args: Array<String>) {
    seq.take(3).forEach(::println)
    seq.take(2).forEach(::println)
    seq.takeWhile{it < 5}.forEach(::println)
}
val seq: Sequence<Int> = sequence {
    println("Start")
    for (i in 1..10) {
        yield(i)
        println("weiter")
    }
    println("Ende.")
}

