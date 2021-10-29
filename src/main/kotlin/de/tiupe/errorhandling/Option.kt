package de.tiupe.errorhandling
/***
 * Diese Klasse repräsentiert die Option-Klasse wie in Scala, um voll funktional programmieren zu können.
 * Zur Idee siehE @see [readme.md](./readme.md)
 * */
sealed  class Option<out A>

data class Some<out A>(val get: A) : Option<A>()

object None : Option<Nothing>()

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = TODO()

fun <A> Option<A>.getOrElse(default: () -> A): A = TODO()

fun <A> Option<A>.orElse(ob: () -> Option<A>): Option<A> = TODO()

fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> = TODO()


/**
 * Mit dieser Option kann man jetzt eine Mittel-Wert-Funktion definieren, die auch für leere Listen sinnvoll ist, ohne
 * Fehler zurückzugeben:
 * */
fun mittelwert(xs: List<Double>): Option<Double> =
    if (xs.isEmpty())
        None
    else Some(xs.sum() / xs.size)

