package de.tiupe.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ContentOfNestedStructure(val attribute1: String, val attribute2: String)

@Serializable
data class NestedStructure(val attribute: String, val contentOfNestedStructure: ContentOfNestedStructure)

fun main() {
    val contentOfNestedStructure: ContentOfNestedStructure = ContentOfNestedStructure("Peter", "Wars")
    val contentOfNestedStructure_1 = ContentOfNestedStructure("schon", "wieder")

    val nestedStructure = NestedStructure("Schön wärs", contentOfNestedStructure)

    val jsonStringUnformatted = Json.encodeToString(nestedStructure)
    println("Unformaiterte Ausgabe: ${jsonStringUnformatted}" )


    // Dies ist ein schönes Beisiel für den Aufruf eines Lambdas with Receiver.
    // Das Lambda ist folgendermaßen definiert:

    // public fun Json(from: Json = Json.Default, builderAction: JsonBuilder.() -> Unit): Json

    // Das Lambda-With-Receiver Objekt sorgt also dafür, den Json-Builder mit entsprechenden Attributen
    // anzureichern, bzw. diese zu ändern.

    val json = Json {
                          this.prettyPrint = true
                          this.prettyPrintIndent = "     "
                          this.isLenient = false
                }

    val jsonStringFormatted = json.encodeToString(nestedStructure)
    println("Formatierte Ausgabe: $jsonStringFormatted")
}