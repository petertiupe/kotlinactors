package de.tiupe.errorhandling
/***
 * Diese Klasse repräsentiert die Option-Klasse wie in Scala, um voll funktional programmieren zu können.
 * Zur Idee siehE @see [readme.md](./readme.md)
 * */
sealed  class Option<out A>

data class Some<out A>(val get: A) : Option<A>()

object None : Option<Nothing>(){
    override fun toString(): String {
        return "None"
    }
}

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = if(this == None) None else Some(f((this as Some).get))

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = if(this == None) None else {
    val valOfOpt = (this as Some).get
    f(valOfOpt)
}

fun <A> Option<A>.getOrElse(default: () -> A): A = if(this == None) default() else (this as Some).get

fun <A> Option<A>.orElse(ob: () -> Option<A>): Option<A> = if(this == None) ob() else this

fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> = if(this == None) None else {
        val valOfOpt = (this as Some).get
        if(f(valOfOpt))
            this
        else
            None
}


/**
 * Mit dieser Option kann man jetzt eine Mittel-Wert-Funktion definieren, die auch für leere Listen sinnvoll ist, ohne
 * Fehler zurückzugeben:
 * */
fun mittelwert(xs: List<Double>): Option<Double> =
    if (xs.isEmpty())
        None
    else Some(xs.sum() / xs.size)

fun main(){
    // Test Map
    val a = Some(5)
    val b: Option<Int> = a.map{it + 1}
    println(b)

    val c: Option<Int> = None
    val d = c.map{it + 1}
    println(d)

    // Test getOrElse
    println(a.getOrElse { 99 })
    println(c.getOrElse { 99 })

    // Test filter
    println(a.filter{it > 1})
    println(a.filter{it > 99})
    println(b.filter{it > 7})

    // Test flatMap
    println(a.flatMap { Some(it + 9) })
    println(c.flatMap { Some(it + 9) })

    println(a.orElse { Some(55) })
    println(c.orElse { Some(55) })
}

