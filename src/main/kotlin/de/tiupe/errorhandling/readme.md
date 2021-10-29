# Excptions in Kotlin
## Exception-Handling
Während in Java noch alle Checked-Exceptions abgefangen werden müssen (alle nicht von Runtime-Exception 
abgeleiteten Exceptions), geht Kotlin hier einen anderen Weg. Es müssen keine Exceptions abgefangen werden. Wenn 
Exceptions abgefangen werden, gilt eine ähnliche Syntax wie in Java:

```kotlin
    try {
         ...
     } catch (irgendeEineException: IrgendEineException) {
         ...
     } catch (irgendEineAndereException: IrgendEineAndereException) {
         ...println(ex)
     } finally {
         ...
     }
```
Insbesondere kann ein finally-Block wie in Java definiert werden.

## Exceptions in Kotlin werfen
Das Werfen einer Exception ist ähnlich einfach wie das Fangen:
````kotlin
    fun irgendEineFunktion(...) : Irgendwas {
        throw IrgendEineExcepion
    }
````
Wie man sieht, benötigt man keine throws-Klausel wie in Java, sondern kann den Fehler einfach irgendwo werfen.

## try als Ausdruck
```try``` hat in Kotlin wie auch ```if``` oder ```when``` einen Rückgabewert. Man kann also den Ausdruck einer
Variblen zuweisen:

````kotlin
    val variable = try {
        6 / 0
    } catch (npex: NullpointerException) {
        1
    }
````

Die obige Anweisung ist gültig und weist der Variablen immer den Wert 1 zu, da die Division durch 0 nicht definiert ist.

## Fehlerhandling aus funktionaler Sicht
