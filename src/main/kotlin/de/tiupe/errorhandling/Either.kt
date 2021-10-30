package de.tiupe.errorhandling


/***
 * Diese Klasse repräsentiert die Either-Klasse wie in Scala, um voll funktional programmieren zu können.
 * Zur Idee siehE @see [readme.md](./readme.md)
 * */
sealed class Either<out E, out A>

data class Left<out E>(val value: E) : Either<E, Nothing>()

data class Right<out A>(val value: A) : Either<Nothing, A>()

