package de.tiupe.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/*
* Dies ist ein erstes Beispiel für eine Serialisierung mit Kotlins-Serialisierungs-Bibliothek.
* Gedacht sein soll hier an ein Quiz mit einer Frage und mehreren Antwortmöglichkeiten.-
*
* Dies soll ein Beispiel zum Testen der Funktionalität sein. Ohne das
* "Serializer" - Plugin in der build-gradle-Datei wird der folgende Fehler geworfen:
*
*   "Mark the class as @Serializable or provide the serializer explicitly."
*
* obwohl die Klasse mit @Serializable markiert ist.
*
* Mit dem folgenden Eintrag läuft das Programm sofort:
* id("org.jetbrains.kotlin.plugin.serialization") version "1.4.30"
*
* oder auch so:
* kotlin("plugin.serialization") version "1.4.30"
*
* Das Plugin sorgt dafür, dass die "Serialisierer" geschrieben werden.
*
* Es werden lediglich Properties serialisiert, für die es ein Backing-Field gibt, einfach ausgedrückt,
* die eine echte Property mit Getter und Setter sind.
*
*
* */

@Serializable
class Auswahl(val text: String, val ok: Boolean)

@Serializable
class Frage(val text: String, val auswahlmoeglichkeiten: List<Auswahl>)

fun main() {
    try {
        val frage1 = Frage(
            "Wie kalt ist die tiefste Temperatur im Braunschweiger Winter 2021 gewesen?",
            listOf(Auswahl("+5", false), Auswahl("-20", true), Auswahl("-25", false))
        )

        val frage2 = Frage(
            "Wie hoch war die Höchsttempertur im Sommer 2020 in Braunschweig?",
            listOf(Auswahl("+35", false), Auswahl("-42,5", true), Auswahl("48,2", false))
        )


        val jsonFrage1: String = Json.encodeToString(frage1)
        println("Die erste Frage ist serialisiert:\n$jsonFrage1")

        val frageDeserialisiertAusSerialisierung = Json.decodeFromString<Frage>(jsonFrage1)
        println("Das deserialisierte Objekt kann man auswerte: ${frageDeserialisiertAusSerialisierung.text}")

        // Und hier noch die entsprechenden Listen-Beispiele:
        val fragenListe = listOf<Frage>(frage1, frage2)

        val fragenListeJson = Json.encodeToString(fragenListe)

        println("Die Liste als Json: $fragenListe")

        // und wieder zurück:
        val fragenListeDeserialisiert = Json.decodeFromString<List<Frage>>(fragenListeJson)
        println("Auch die Liste kann man nach Eigenschaften fragen: ${fragenListeDeserialisiert.count()}")
    } catch(sex: SerializationException) {
        println("Bei der Serialisierung gab es ein Problem. Dies sollte immer so abgefangen werden, " +
                "dass man die Exception fängt.")
    }
}