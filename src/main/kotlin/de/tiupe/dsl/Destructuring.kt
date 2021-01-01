package de.tiupe.dsl

class PetersRange(val from: Int, val to: Int){

    operator fun component1() = this.from
    operator fun component2() = this.to
}

fun main(){
    val myRange = PetersRange(1, 42)
    val (a,b) = myRange

    // liefert: Die Range geht von 1 bis 42
    println("Die Range geht von $a bis $b")
}