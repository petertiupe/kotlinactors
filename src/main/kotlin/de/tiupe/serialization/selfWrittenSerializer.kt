@file:UseSerializers(LocalDateSerializer::class)
package de.tiupe.serialization
import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/*
* Per default können lediglich elementare Datentypen serialisiert und deserialisiert werden.
* Hier ein Beispiel für einen expliziten Serialisierer am Beispiel der LocalDate - Klasse
*
* Man überschreibt das Interface KSerializer mit den beiden Funktionen
*
*       "serialize"
*
* und
*
*       "deserialize"
*
* Ganz wichtitig ist der @file-Eintrag ganz oben in der Klassendatei. Er legt den Serialisierer für
* die gesamte Datei fest.
*
* */

// Den encoder sehe ich erst einmal als eine Hilfsklasse an, die Funktionen für alle
// Kotlin-Basisklassen zur Verfügung stellt, die dann an den "Basis-Serialisierer" übergeben werden
// können. Wie schon erwähnt, sind lediglich die Baisklassen per Default serialisierbar.
@Serializer(LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(DateTimeFormatter.ISO_DATE))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), DateTimeFormatter.ISO_DATE)
    }
}

@Serializable
data class Person(val vorname: String, val nachname: String , val gebTag: LocalDate)


fun main() {
    val peter = Person("Peter", "Marx", LocalDate.of(1966, 10, 1))
    val jsonPeter = Json.encodeToString(peter)
    println("Das serialisierte Objekt sieht so aus: $jsonPeter")

    val peterVonJson = Json.decodeFromString<Person>(jsonPeter)
    println("Nach der Deserialisierung kann man auf das Objekt zugreifen: ${peterVonJson.gebTag.dayOfYear}")
}