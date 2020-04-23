package de.tiupe.properties

/**
 *
 * Man kann einen Konstruktor so aufbauen, dass Felder eines Objekts aus einer Map
 * gefüllt werden. Dies kann insbesondere dann sinnvoll sein, wenn Werte bereits als
 * Key-Value-Paare z.B. aus einer Schnittstelle vorliegen.
 *
 * Die Map, die im Konstruktor übergeben wird, steht dann in der Delegation hinter dem
 * "by" - Ausdruck wie in dem Beispiel.
 *
 * */
class UserByMap(mapOfProperties: Map<String, Any>){
    val vorname: String by mapOfProperties
    val nachname: String by mapOfProperties
    val alter: Int by mapOfProperties
}

fun main(){
    val userList: List<Map<String, Any>> = listOf<Map<String, Any>>(
            mapOf( "vorname" to "Peter", "nachname" to "Marx", "alter" to 53),
            mapOf( "vorname" to "Tina", "nachname" to "Feddersen", "alter" to 52)
    )

    // In der Schleife werden erst die Objekte aus der Map erzeugt und dann die Eigenschaften
    // Auf die Console geschrieben.
    userList.forEach {
        val user = UserByMap(it)

        println("VN -> ${user.vorname}")
        println("NN -> ${user.nachname}")
        println("AL -> ${user.alter} \n")

    }
}