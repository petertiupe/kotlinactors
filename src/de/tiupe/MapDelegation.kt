package de.tiupe

class MapUser(map: Map<String, Any>) {
    val vorname: String by map
    val nachname: String by map
    val alter: Int by map
}



fun main() {
    val peter: MapUser = MapUser(mapOf("vorname" to "Peter", "nachname" to "Marx", "alter" to 42 ))
    println(peter.nachname)
}