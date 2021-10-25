# Kotlins-Scope-Functions
Es gibt in Kotlin fünf sogenannte Scope-Functions:
 ```with```, ```run```, ```let```,  ```apply``` und ```also```

Am einfachsten erklären sie sich durch ihre Signaturen:
```kotlin
public inline fun <T, R> with(receiver: T, block: T.() -> R): R

public inline fun <T, R> T.run(block: T.() -> R): R

public inline fun <T, R> T.let(block: (T) -> R): R

public inline fun <T> T.apply(block: T.() -> Unit): T

public inline fun <T> T.also(block: (T) -> Unit): T 
```

<b><u>
Der wichtige Unterschied zwischen den Funktionen besteht in ihrem Rückgabetyp. Die ersten drei Funktionen
haben die Möglichkeit, einen anderen als den Empfängertyp zurückzugeben. ```apply``` und ```also``` geben beide den
Typen zurück, auf dem sie auch aufgerufen wurden.
</u></b>

## with
Die ```with```-Funktion bekommt das Objekt auf dem sie arbeitet, explizit in dem ersten Parameter übergeben.
Da es sich hier um eine Function-With-Receiver handelt, steht in dem Funktionsblock ein this-Objekt vom Typ
Student zur Verfügung, sodass der folgende Code gültig ist.
````kotlin
with(myStudent){
 println(nachname)
 println(vorname)
} 
````

## run
Auch ```run``` hat eine Function-With-Receiver als einzigen Parameter. Damit ist der einzige Unterschied zu ```with```, 
dass die Funktion auf einem Objekt aufgerufen wird, während das Objekt bei ````with```` übergeben wird. Der Typ des
Lambdas ist jeweils derselbe.
````kotlin
val vornameA: String? = myStudent.run {
        this.vorname
    }
````

## let
Bei ```let``` ist der Parameter als ganz normales Lambda definiert und damit hat man dan ```it```-Parameter zur Verfügung
und nicht das Objekt.
```kotlin
val nachnameB: String? = myStudent.let {
        it.nachname
    }
```

## apply und also
```apply``` und ```also``` unterscheiden sich, wie oben schon erwähnt, nur durch das Objekt, auf dem sie arbeiten, sie
geben beide ein Objekt vom Typ zurück, mit dem sie aufgerufen werden.


```apply``` arbeitet mit dem ```this```-Objekt während ```also``` ein normales Lambda verarbeitet:
````kotlin
val myStudent: Student = Student().also{
        // also kennt 'it'
        it.nachname = "Marx"
        it.vorname  = "Peter"
        println(it)
    }.apply {
        // apply kennt 'this', kann also auch ohne auf die
        // Parameter zugreifen...
        this.vorname = "Tina"
        this.nachname ="Feddersen"
        println(this)
    }
```