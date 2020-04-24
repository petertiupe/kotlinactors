package de.tiupe.functiononcollection

class Person(var name: String, var vorname: String, var alter: Int)


/**
 * Der groupBy-Befehl erzeugt eine Map, in der der Parameter nach dem gruppiert wird
 * als Key genommen und sämtliche Werte die dazu passen als Liste dahinter gehängt werden.
 *
 * In dem folgenden Beispiel wird nach dem "Namen" der Person gruppiert
 *
 * Das Programm erzeugt die folgende Ausgabe:
 *
 *      Schlüssel: Feddersen
 *      Tina, 52
 *      Lara, 16
 *      Inken, 14
 *
 *      Schlüssel: Marx
 *      Peter, 52
 *
 *      Schlüssel: Jule
 *      Stinkt, 16
 *
 * */
fun main(){
    val myList = listOf<Person>(Person("Feddersen", "Tina", 52), Person("Feddersen", "Lara", 16),
            Person("Feddersen", "Inken", 14 ), Person("Marx", "Peter", 52 ), Person("Jule", "Stinkt", 16 ))

    val groupedMap: Map<String, List<Person>> = myList.groupBy { it.name }

    for (entry in groupedMap) {
        println("\nSchlüssel: ${entry.key}")
        entry.value.forEach { person: Person -> println("${person.vorname}, ${person.alter}") }
    }

}