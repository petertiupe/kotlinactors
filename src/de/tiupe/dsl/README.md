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
wie eine eigene Grammatik aussehen zu lassen, im innern aber dennoch alle eben gennaten Tools
zur Verfügung zu haben. Wie das funktionieren kann, zeigen die Prgramme in diesem Paket.

###Operator-Overloading
Um eine DSL nach außen wie eine eigene Sprache aussehen zu lassen, ist ein erster
Schritt das Operator-Overloading. In Kotlin sehr einfach möglich, indem man vor eine
Funktion (Member-Funktion oder auch Extension-Funktion) das operator Schlüsselwort
schreibt.

    operator fun String.times(n: Int) = repeat(n)
