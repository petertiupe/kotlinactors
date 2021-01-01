package de.tiupe.functiononcollection

class PersonAssociate(var name: String, var vorname: String, var alter: Int)

/**
 * Die associateBy-Funktion macht aus einer Collection eine Map. Der "Selektor"
 * der der Funktion übergeben wird ist der Key für die Map
 *
 * ACHTUNG: Falls zwei Objekte denselben Selektor haben, wird der zweite Wert
 * =======  von den beiden gelöscht. Wenn das nicht das ist, was man möchte,
 *          sollte der Selektor unique sein.
 *
 * Die Ausgabe des Programms ist:
 *
 *      Da Jule in der Liste so alt ist wie Lara und Peter so alt ist wie Tina,
 *      überschreiben die beiden letztgenannten die zuerst genannten ...
 *      Die associate-Liste hat eine Länge von 3 während die Ursprungsliste 5 Elemente enthielt
 *      Schlüssel: 52
 *      Wert: Peter
 *
 *      Schlüssel: 16
 *      Wert: Stinkt
 *
 *      Schlüssel: 14
 *      Wert: Inken
 *
 * */



fun main(){
    val myList = listOf<PersonAssociate>(PersonAssociate("Feddersen", "Tina", 52), PersonAssociate("Feddersen", "Lara", 16),
            PersonAssociate("Feddersen", "Inken", 14 ), PersonAssociate("Marx", "Peter", 52 ), PersonAssociate("Jule", "Stinkt", 16 ))

    val assocMap: Map<Int, PersonAssociate> = myList.associateBy { it.alter }


    println("Da Jule in der Liste so alt ist wie Lara und Peter so alt ist wie Tina, überschreiben die beiden letztgenannten " +
            "die zuerst genannten ...")

    println("Die associate-Liste hat eine Länge von ${assocMap.size} während die Ursprungsliste ${myList.size} Elemente enthielt")
    for (entry in assocMap) {
        println("Schlüssel: ${entry.key}")
        println("Wert: ${entry.value.vorname} \n")
    }

}