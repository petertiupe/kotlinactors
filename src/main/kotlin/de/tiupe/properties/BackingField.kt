package de.tiupe.properties

/**
 * Die Deklaration von Properties hat in Kotlin die allgemeine Syntax:
 *
 *      var <propertyName>[: <PropertyType>] [= <property_initializer>]
 *      [<getter>]
 *      [<setter>]
 *
 * // Mein Problem war das folgende:
 *
 * class TestClass(){
        private val petersProp: String = "Peters Inhalt"
        get() = this.petersProp
   }
 *
 * liefert den Fehler "Initializer not allowed here, because this property has no backing-field"
 *
 * Jetzt ist also die Frage, wie komme ich an das Backing Field?
 *
 * Die Antwort ist ganz einfach, in den Gettern und Settern zu einer Property arbeitet man nicht
 * mit der Property selbst, sondern mit dem sogenannten Backing-Field, die einfach als
 *
 *      "field"
 *
 * wie hier gezeigt angesprochen wird.
 *
 * */

class TestClass(){
    // Sowohl der hier gezeigte Getter als auch der entsprechende Setter sind redundant und
    // werden von Intellij auch so markiert. Hier stehen sie zu Dokumentationszwecken
    var petersProp: String = "Peters Inhalt"
       get() = field
       set(pub: String) {field = pub}
}

fun main(){
    val testObj = TestClass()
    println("Vor der Änderung: ${testObj.petersProp}")
    testObj.petersProp="Tinas Inhalt"
    println("Nach der Änderung: ${testObj.petersProp}")
}