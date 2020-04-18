# Domain-Specific-Languages in Kotlin

In diesem Paket programmiere ich die Beispiele aus dem DSL Kapitel aus dem Buch

Sedunov, Aleksei. Kotlin In-depth [Vol-II]: 
A comprehensive guide to modern multi-paradigm language (English Edition). 
BPB Publications. Kindle-Version.  

nach. 

DSLs haben in der Regel eine eigene Grammatik. Dies hat den Nachteil, dass IDEs und
Code-Checker sowie weitere, heute zum Standard einer Programmiersprache zur Verfügung
stehende Tools für die DSL nicht vorhanden sind.
Eine DSL in Kotlin zu schreiben hat den Vorteil, dass es möglich ist, die DSL nach außen
wie eine eigene Grammatik aussehen zu lassen, im Innern aber dennoch alle eben genannten Tools
zur Verfügung zu haben. Wie das funktionieren kann, zeigen die Prgramme in diesem Paket.

### Operator-Overloading
Um eine DSL nach außen wie eine eigene Sprache aussehen zu lassen, ist ein erster
Schritt das Operator-Overloading. In Kotlin sehr einfach möglich, indem man vor eine
Funktion (Member-Funktion oder auch Extension-Funktion) das operator Schlüsselwort
schreibt.

    operator fun String.times(n: Int) = repeat(n)

Die so erweiterte Klasse kann man dann nahtlos nutzen:

    println("\nPeter ist der Beste kann man nicht oft genug lesen" * 5)
    
Der Vollständigkeit halber füge ich hier noch die Tabelle aus der Kotlin Dokumentation
hinzu. Neben den binären gibt es auch einige unäre Operatoren, die man in Kotlin überladen
kann.

<table>
<thead>
<tr>
<th>Ausdruck</th>
<th>wird übersetzt zu</th>
</tr> 
</thead>   
<tbody>
<tr>
<td><code>+a</code></td>
<td>a.unaryPlus()</td>
</tr>  
<tr><td><code>-a</code></td>
<td>a.unaryMinus()</td></tr>  
<tr><td><code>!a</code></td>
<td>a.not()</td></tr>
<tr>
<td><code>a++</code></td>
<td><code>a.inc()</code></td>
</tr>
<tr>
<td><code>a--</code></td>
<td><code>a.dec()</code></td>
</tr>
</tbody>
</table>

Und hier nun noch die binären Operatoren  
<table>
<thead>
<tr>
<th>Ausdruck</th>
<th>wird übersetzt zu</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>a + b</code></td>
<td><code>a.plus(b)</code></td>
</tr>
<tr>
<td><code>a - b</code></td>
<td><code>a.minus(b)</code></td>
</tr>
<tr>
<td><code>a * b</code></td>
<td><code>a.times(b)</code></td>
</tr>
<tr>
<td><code>a / b</code></td>
<td><code>a.div(b)</code></td>
</tr>
<tr>
<td><code>a % b</code></td>
<td><code>a.rem(b)</code>, <code>a.mod(b)</code> (deprecated)</td>
</tr>
<tr>
<td><code>a..b </code></td>
<td><code>a.rangeTo(b)</code></td>
</tr>
<tr>
<td><code>a in b</code></td>
<td><code>b.contains(a)</code></td>
</tr>
<tr>
<td><code>a !in b </code></td>
<td><code>!b.contains(a)</code></td>
</tr>
</tbody>
</table>

Neben den bisher gezeigten Operatoren zum Überschreiben gibt es noch die 
Vergleichsoperatoren. Auch diese seien hier kurz aufgeführt:
<table>
<thead>
<tr>
<th>Ausdruck</th>
<th>wird übersetzt zu</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>a < b</code></td>
<td><code>a.compareTo(b) < 0</code></td>
</tr>
<tr>
<td><code>a <= b</code></td>
<td><code>a.compareTo(b) <= 0</code></td>
</tr>
<tr>
<td><code>a > b</code></td>
<td><code>a.compareTo(b) > 0</code></td>
</tr>
<tr>
<td><code>a >= b</code></td>
<td><code>a.compareTo(b) >= 0</code></td>
</tr>
</tbody>
</table>

### Infix-Funktionen als weiteres Sprachmittel zum Aufbau einer DSL
In der Datei InfixOperations kann man sehen, wie man Infix-Funktionen
gut dafür nutzen kann, Funktionsaufrufe wie normale Sprache aussehen zu lassen.  
Das Beispiel in der Datei sollte mit den Kommentaren selbsterklärend sein.

### Augmented Assignment (+=, *=, /=, -=, %=)
<table>
<thead>
<tr>
<th>Ausdruck</th>
<th>wird übersetzt nach</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>a += b</code></td>
<td><code>a.plusAssign(b)</code></td>
</tr>
<tr>
<td><code>a -= b</code></td>
<td><code>a.minusAssign(b)</code></td>
</tr>
<tr>
<td><code>a *= b</code></td>
<td><code>a.timesAssign(b)</code></td>
</tr>
<tr>
<td><code>a /= b</code></td>
<td><code>a.divAssign(b)</code></td>
</tr>
<tr>
<td><code>a %= b</code></td>
<td><code>a.remAssign(b)</code>, <code>a.modAssign(b)</code> (deprecated)</td>
</tr>
</tbody>
</table>  
Bei den Augmenteded Assignments gilt es zu beachten, wie der Compiler diese auswertet.  
Hier am Beispiel a += b führt der Compiler folgende Schritte durch  

Wenn die Funktion auf der rechten Seite der Tabelle definiert ist und

1. die entsprechende binäre Funktion, in diesem Fall + ebenfalls vorhanden ist => Fehler, da nicht eindeutig auswertbar  
2. prüfe, dass der Typ der Funktion aus der Tabelle Unit ist (es wird die Originalvariable verändert), wenn nicht wird ein Fehler gemeldet  
3. erzeuge den Code für <code>a.plusAssign(b)</code>  

Wenn die Funktion auf der rechten Seite der Tabelle nicht definiert ist  

1. versuche den Code für <code>a = a + b</code> zu erzeugen, dabei wird auch geprüft, dass der Typ von a + b ein Subtyp von a ist.

Achtung!: Assignements sind in Kotlin keine Ausdrücke, d.h. man kann die Zuweisung
nicht an das Ende eines Codeblocks als Rückgabewert schreiben.
