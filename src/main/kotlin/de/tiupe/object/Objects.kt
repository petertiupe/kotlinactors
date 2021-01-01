package de.tiupe.`object`

open class TestKlasseMitAttribut(val name: String = "")


/**
 * Anonyme Objekte sind solche, die keiner variablen zugewiesen werde, die also nicht weiter verwendet
 * werden können.
 *
 * Daneben gibt es Objekte, die nur ein einziges Mal benötigt werden. Für diese legt man keine Klasse
 * an, sondern erzeugt sogleich ein Object. Beispiel dafür ist eine Factory, die sollte sogar immer als
 * Singleton vorliegen.
 *
 *
 *
 * */

object FactoryFuerTestklasse {
    private val listOfObjects: MutableList<TestKlasseMitAttribut> = mutableListOf()

    fun createOrGetTestKlasseMitAttribut(name: String): TestKlasseMitAttribut {
        val x = listOfObjects.find { it.name == name }
        if(x == null){
            listOfObjects.add(TestKlasseMitAttribut(name))
        }
        return  listOfObjects.find {it.name == name}!!
    }
    fun getCacheSize(): Int = listOfObjects.size
}


/**
 * Hier wird die Klasse entsprechend getestet. Die Ausgabe ist:
 *
 *      So sieht der Aufruf eines anonymen Objekts aus: Peter wars
 *      Man kann sogar noch einen Schritt weitergehen und auch die Klasse anonym lassen:
 *      Jetzt hat man ein Objekt, dessen Klasse keinen Namen hat, von dem es aber einen Aufruf machen kann:
 *
 *      Zugriff auf das anonyme Objekt: Dieses Objekt kenne nur ich
 *      Die Liste aus der Factory ist hoffentlich leer: 0
 *      Nachdem zweimal dasselbe Objekt hinzugefügt wurde, sollte die Liste nur ein Objekt enthalten: 1
 *      Nachdem ein zweites Objekt hinzugefügt wurde, sollte die Liste zwei Objekte enthalten: 2
 *
 *
 * Die Factory für die Testklasse hat noch ein Problem, sie kann
 * */
fun main(){
    // Objekt der Klasse anlegen, als anonymes Objekt
    println("So sieht der Aufruf eines anonymen Objekts aus: ${TestKlasseMitAttribut("Peter wars").name}")

    println("Man kann sogar noch einen Schritt weitergehen und auch die Klasse anonym lassen:")
    val a = object: TestKlasseMitAttribut("Peters anonymes Objekt"){
        var zweiterName = ""
    }

    println("Jetzt hat man ein Objekt, dessen Klasse keinen Namen hat, von dem es aber" +
            " einen Aufruf machen kann: \n")
    a.zweiterName = "Dieses Objekt kenne nur ich"
    println("Zugriff auf das anonyme Objekt: ${a.zweiterName}" )


    println("Die Liste aus der Factory ist hoffentlich leer: ${FactoryFuerTestklasse.getCacheSize()}")
    FactoryFuerTestklasse.createOrGetTestKlasseMitAttribut("Tina")
    FactoryFuerTestklasse.createOrGetTestKlasseMitAttribut("Tina")
    println("Nachdem zweimal dasselbe Objekt hinzugefügt wurde, sollte die Liste nur ein Objekt enthalten: " +
            "${FactoryFuerTestklasse.getCacheSize()}")
    FactoryFuerTestklasse.createOrGetTestKlasseMitAttribut("Peter")

    println("Nachdem ein zweites Objekt hinzugefügt wurde, sollte die Liste zwei Objekte enthalten: " +
            "${FactoryFuerTestklasse.getCacheSize()}")
}