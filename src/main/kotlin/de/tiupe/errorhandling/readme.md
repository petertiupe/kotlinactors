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
Aus funktionaler Sicht verliert man beim Werfen von Fehlern die Kontrolle über sein Programm. Entweder wird das Programm
aufgrund eines Fehlers beendet, weil dieser nicht abgefangen wurde, oder die Fehlerbehandlung findet an ganz anderer Stelle
statt und damit steigt die Komplexität des Prgramms unerwünschter Weise. 

### Fehler brechen die referentielle Integrität
Unter der referentiellen Integrität versteht man die Tatsache, dass man bei der funktionalen Programmierung jeden 
Funkionsaufruf durch das Ergebnis des Funktionsaufrufs ersetzen darf. Was macht man nun aber, wenn ein Fehler bei
einer Funktion geworfen wird? Der Wert einer Funktion sollte niemals von den Aufrufbedingungen abhängen. Exceptions haben
zwei Hauptprobleme:

<ul> 
    <li>sie brechen die referentielle Integrität und führen damit Context-Abhängigkeit ein.</li>
    <li>sie sind nicht typsicher. Wenn man vergisst, eine Exception zu checken, bemerkt man den Fehler erst
        zur Laufzeit
    </li>
</ul>

Insbesondere tritt eine solche Problematik bei den Higher-Order-Functions auf. Man kann in ihnen nicht alle möglichen
Fehlersituationen vorhersehen, daher benötigt man die hier angedachten Alternativen, die z. B. auch von einem 
Framework wie ```Arrow``` vorgesehen sind. Auch partielle Funktionen sind ein Beispiel für die hier gezeigte
Problematik. 

#### Die alternative Lösung für das Werfen von Fehlern
Statt Fehler zu werfen, gibt man einen Wert zurück, der die fehlerhafte Situation anzeigt. Diese Lösung ist typsicher, 
sodass der Typechecker schon fehlerhafte Quellen finden kann.

#### Optional ist eine der Lösungen
In der Datei [Option](./Option.kt) ist eine Option ähnlich zu der in Scala definiert. Man kann durch das None-Objekt
anzeigen, dass eine Funktion keinen sinnvollen Wert zurückgibt. Kotlin ist hier den einfacheren Weg über die 
Nullable-Objects gegangen, allerdings ist die Option bei den Higher-Order-Functions die deutlichere Variante.

Die ```mittelwert```-Funktion in der genannten Klasse ist ein Beispiel für eine Higher-Order-Function, die ohne
Fehlerhandling auskommt.

#### Either als weitere Lösung
Neben der Möglichkeit, ```Optional```s zurückzugeben, statt Fehler zu werfen, hat man noch die Möglichkeit das von Scala
bekannte ```Either``` zu benutzen. Nutzt man Option, erfährt man nichts über den Hintergrund, was bei dem Aufruf einer Funktion
evtl. fehlerhaft gewesen ist, manchmal möchte man mehr wissen, dann kann man ```Either```  nutzen. ```Either``` kennt wie
```Option``` zwei Zustände, nur dass bei ```Either``` beide Zustände Inhalt mit sich führen, die von unterschiedlichem
Typ sein können. Die Datei [Either](./Either.kt) zeigt den dazugehörigen Code. Oftmals wird Right als der korrekte
Zustand und Left als der fehlerbahaftete Zustand angesehen. Der folgende Code zeigt beispielhaft die Verwendung:
```kotlin
    fun mittelwert(xs: List<Int>): Either<String, Double> =
        if (xs.isEmpty())
            Left("Eine leere Liste hat keinen Mittelwert")
        else 
            Right(xs.sum() / xs.size())
```
Dieselbe Funktion kann man natürlich auch mit ```Option``` realisieren, allerdings steht dann keine Information zur
Verfügung, warum im ersten Teil der Funktion ```None``` zurückgegeben wird:
```kotlin
    fun mittelwert(xs: List<Int>): Option<Double> =
        if (xs.isEmpty())
            None
        else 
            Some(xs.sum() / xs.size())
```

## Zusammenfassung
Das Werfen von Fehler
<ul>
    <li>widerspricht der referentiellen Integrität</li>
    <li>sollte auf Situationen beschränkt werden, in denen kein vernünftiges Objekt zurückgegeben werden kann</li>
    <li>kann durch die oben gezeigten Daten-Typen weitestgehend vermieden werden</li>
</ul>
