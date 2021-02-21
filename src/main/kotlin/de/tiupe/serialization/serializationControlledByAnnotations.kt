package de.tiupe.serialization

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

/*
* Durch die Annotationen
*
*   @SerialName     -->  steuert den Attributnamen
*   @Required       -->  steuert die Serialisierung von Defaultwerten
*   @Transient      -->  schließt von der Serialisierung aus
*
* kann man Einfluss auf die Serialisierung nehmen
* */
    @Serializable
    data class ControlledSerializationObject(
    @SerialName("peterWars") val hasOtherName: String = "Ich heiße anders",
    @Required val isRequired: String="",
    val isNotRequired: String ="",
    @Transient val isNeverSerialized: String="mich sieht man nie :-( ")

fun main() {
    try {
        val objectToSerialize = ControlledSerializationObject(hasOtherName = "Peter")

        val jsonString = Json.encodeToString(objectToSerialize)
        println("Das serialisierte Objekt sieht so aus:\n$jsonString")
        // Liefert den folgenden Output:
        //Das serialisierte Objekt sieht so aus:
        //{"peterWars":"Peter","isRequired":""}

        // ohne das Setzen von hasOtherName:
        // {"isRequired":""}, was daran liegt, dass die Default-Werte nie serialisiert werden.


        val deserializedObject = Json.decodeFromString<ControlledSerializationObject>(jsonString)
        println("Das deserialisierte Objekt hat Eigenschaften: ${deserializedObject.hasOtherName}")

    } catch(sex: SerializationException) {
        println("Es ist ein Fehler aufgetaucht")
    }
}



