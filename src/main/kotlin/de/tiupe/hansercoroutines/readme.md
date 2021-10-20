# Hanser-Coroutines
In diesem Paket habe ich Beispiele aus dem Hanser Kotlin-Buch nachempfunden.
Insbesondere die Kapitel zu den Coroutinen und der Nebenläufigkeit sind sehr 
gut zu lesen.

## Nebenläufigkeit und Parallelität
<p><u><b>Parallele Ausführung</b></u> bedeutet, dass die Verarbeitung wirklich gleichzeitig stattfindet.</p>
<p><u><b>Nebenläufige Ausführung</b></u> besagt lediglich, dass die Verarbeitung entweder parallel oder abwechselnd ausgeführt wird</p>
Der Begriff der parallelen Ausführung ist also strenger gefasst als die Nebenläufigkeit. Beide Begriffe beschreiben die 
<u><b>asynchrone Verarbeitung bzw. asynchrone Algorithmen</b></u>.

### Definition nebenläufiger Algorithmus
<p>
    <i>
        „Zwei Algorithmen A und B können nebenläufig ausgeführt werden, wenn sie unabhängig voneinander ablaufen können. 
        Durch diese Unabhängigkeit kann die Reihenfolge vertauscht werden, d. h. A kann vor B ablaufen und andersherum. 
        Es kann sogar während des Ablaufs zwischen A und B gewechselt werden. Dies ist möglich, da jeder der Algorithmen 
        seinen eigenen Ablaufstrang (Thread) besitzt. Aufgrund der Unabhängigkeit können A und B sogar gleichzeitig, 
        also parallel, ausgeführt werden.“
    </i>

Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“
</p>

## Koroutinen und Threads Ähnlichkeiten und Unterschiede
<ul>
    <li>jeder Thread hat eine eigene unabhängige Ablaufkontrolle und einen eigenen Stack</li>
    <li>Threads werden durch den Scheduler des Betriebssystems gesteuert</li>
    <li>Threadverwaltung ist teuer</li>
    <li>Koroutinen kann man sich wie leichtgewichtige Threads vorstellen, die sich einen Thread teilen können</li>
    <li>Koroutinen arbeiten kooperativ, denn sie blockieren sich nicht</li>
    <li>Koroutinen sorgen dafür, dass die für einen nebenläufigen Algorithmus erfordeliche Ablaufreihenfolge eingehalten wird</li>
    <li>Koroutinen unterstützen dabei, die  sequentiellen Schritte eines Programmablaufs weiterhin als solche abzubilden 
        und gleichzeitig Teilaufgaben nebenläufig abzuarbeiten.
    </li>
    <li>Koroutinen unterbrechen ihre Arbeit, während ein Thread läuft, bis seine Verarbeitung vollständig ist</li>
    <li>so wie mehrere Threads auf einem Prozessor laufen können, können auch mehrere Koroutinen auf einem Thread
        ablaufen. Die Koroutinen selbst sind dabei unabhängig voneinander.
    </li>
</ul>
<u><b>Koroutinen darf man nicht mit suspending functions verwechseln. In einer Koroutine darf man
      suspending Functions verwenden, Koroutinen und suspending Functions sind aber nicht dasselbe...
</b></u>

### Definition Koroutine

<p>
    <i>
        „Eine Koroutine ist ein unabhängig ausführbarer Algorithmus. Mehrere Koroutinen können nebenläufig und parallel 
        ausgeführt werden. Konzeptionell sind Koroutinen sehr ähnlich zu Threads. Koroutinen legen ebenfalls unabhängige 
        Ablaufstränge eines Algorithmus fest. Eine Koroutine ist leichtgewichtiger, benötigt also weniger eigene 
        Ressourcen als ein Thread, und das Zusammenspiel der Koroutinen geschieht kooperativ.“
    </i>

Auszug aus: Christian Kohls. „Programmieren lernen mit Kotlin.“        
</p>

