package de.tiupe.asynchnachbuch

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/*
*   Es gibt einen Artikel bei medium.com von R. Elizarov zu dem Thema.
*
*       https://elizarov.medium.com/coroutine-context-and-scope-c8b255d59055
*
*   Hier halte ich die Essenz daraus fest.
*
*   Coroutine-Context und Scope sind unterschiedliche Namen, die  ähnliche Dinge beschreiben.
*
*   CoroutineContext ist ein Interface und man kann zur Laufzeit jederzeit daraur zugreifen,
*   da jede Coroutine einen Context besitzt.
*
*   Zum Context, der immutable ist, kann man Werte hinzufügen.
*
*   Coroutinen werden von einem Job-Objekt repräsentiert
*
*   Eigentlich geht es hier ja um den Unterschied zwischen dem Scope und dem
*   Coroutine-Context. Der Scope hat nur eine einzige Eigenschaft, nämlich den
*   CoroutineContext. Der Scope selbst ist auch ein Interface.
*
*   Warum also gibt es den Scope? Die Antwort darauf ist, weil der Scope eine andere
*   Aufgabe, einen anderen Sinn erfüllt.
*
*   Beim Aufruf eines launch z.B. kann man einen Context mitgeben. Also hat man zwei
*   CoroutineContexts, einen, auf der die Extension-Function aufgerufen wird und den
*   Übergabeparameter. Beim Aufruf werden nun die beiden Contexte gemerged, wobei der
*   Übergabeparameter die höhere Wichtigkeit besitzt.
*
*   Zur Einleitung sollte das reichen. Der Artikel beschreibt dann noch weiter, wie die
*   CoroutineContexte vererbt werden, das schaue ich mir an, wenn ich es brauche.
*
* */



fun main(){
    runBlocking {
        zugriffAufCoroutineContext()

        val x = addValueToCoroutineContext("Peters Coroutine")
        val y = x.get(CoroutineName.Key)
        println("Und hier ist der Name der Coroutine... $y")

        println("So holt man den Job: \${coroutineContext[Job]}")
        println("Und hier ist er: ${coroutineContext[Job]}")

        // Und hier zur Doku noch
        val scopeVar = this.coroutineContext
        // man ist in einem CoroutineScope, daher kann man auf den coroutinecontext zugreifen.

    }
}


suspend fun zugriffAufCoroutineContext(){
    println("Auf die Instanz kann man per \$coroutineContext zugreifen.")
    println("Inhalt ist: $coroutineContext ")
}

suspend fun addValueToCoroutineContext(coroutineName: String): CoroutineContext {
    // mit dem + Operator kann man dem Context Werte hinzufügen.
    return coroutineContext.plus(CoroutineName(coroutineName))
}