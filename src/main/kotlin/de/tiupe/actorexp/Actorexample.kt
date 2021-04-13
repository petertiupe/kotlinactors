package de.tiupe.actorexp

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

// Die Inhalte einer Sealed - Klasse müssen alle in einer Datei stehen.
// Das hat den Vorteil, dass man die Klassen wie eine Aufzählung abschließend in einem
// When-Ausdruck verwenden kann.

// Dies ist die Nachricht, die in dem Actor-System ausgetauscht wird.
// Alle Informationen zwischen den Actors werden über eine Message ausgetauscht, die um
// abschließend zu sein, von einem bestimmten Typ erbt, mehr ist das hier nicht.
// Hier steht neuer Testkommentar
sealed class AccountMessage

// holt den Kontostand, der Übergabeparameter ist der Rückgabewert für die aufrufende Coroutine.
class GetBalance(val amount: CompletableDeferred<Long>) : AccountMessage()

// Einzahlung
class Deposit(val amount: Long) : AccountMessage()

// Auszahlung. Der Boolean ist wieder der Rückgabewert an die aufrufende Coroutine und enthält die
// Information ob die Auszahlung erfolgreich war oder nicht (Deckung vorhanden?)
// CompleteDeferred ist ein "WrapperObjekt" ähnlich einem Future und wird vom Framework
// zurückgegeben, wenn die suspending-Function beendet wurde.
class Withdraw(val amount: Long, val isPermitted: CompletableDeferred<Boolean> ) : AccountMessage()

// Die Funktion gibt einen Account-Manager zurück. Er ist vom Typ SendChannel. Auf diesem Channel sendet
// die main-Funktion die Nachrichten, die dann verarbeitet werden...
@ObsoleteCoroutinesApi
fun CoroutineScope.accountManager(initialBalance: Long ): SendChannel<AccountMessage> {
    // Hier wird der sogenannte Actor-Builder aufgerufen
    // Ergebnis des Aufrufs ist ein SendChannel, an diesen Send-Channel werden anschließend die Nachrichten gesendet
    // und es wird ausgewertet, was mit der Nachricht passieren soll.
    return actor<AccountMessage> {

        // Das Konto wird mit dem Eröffnungskontostand gefüllt,
        // balance ist das eigentliche Attribut, welches von der außenwelt nicht erreichbar ist und den "Status"
        // des Kontos hält.
        // Dadurch dass initialBalance weder var noch val ist, gibt es anschließend auch kein Attribut, das von außen
        // gesetzt werden kann.
        // Dasselbe gilt für die valance, die weder getter noch Setter hat und somit von außen auch nicht modifizier-
        // bar ist. Aus dem Grund muss die Variable auch nicht als private deklariert werden

        var balance = initialBalance

        for (message in channel) {
            // Hier kommt jetzt die "Sealed-Class" zum tragen. Ohne die Sealed-Class müssten in dem when Ausdruck
            // ein default-Fall stehen, so nicht. message ist eine von den o.g. Klassen...
            when (message) {
                // complete wird auf dem CompletableDeferred-Objekt aufgerufen und liefert das Ergebnis in den Channel
                is GetBalance -> message.amount.complete(balance)

                is Deposit    -> {
                    balance += message.amount
                    println("Deposited ${message.amount}")
                }

                is Withdraw   -> {
                    val canWithdraw = balance >= message.amount
                    if (canWithdraw) {
                        balance -= message.amount
                        println("Withdrawn ${message.amount}")
                    }
                    // zu Complete siehe oben beim GetBalance-Aufruf...
                    message.isPermitted.complete(canWithdraw)
                }
            }
        }
    }
}

// Die Nachrichten sollen über einen Channel an die Actoren gelangen, daher sind die Aufrufe als ExtensionFunctions
// beim SendChannel implementiert.
private suspend fun SendChannel<AccountMessage>.deposit(name: String, amount: Long ) {
    send(Deposit(amount))
    println("$name: deposit $amount") }

private suspend fun SendChannel<AccountMessage>.tryWithdraw(name: String, amount: Long) {
    val status = CompletableDeferred<Boolean>().let{
        send(Withdraw(amount, it))
        if (it.await()) "OK" else "DENIED"
    }
    println("$name: withdraw $amount ($status)")
}

private suspend fun SendChannel<AccountMessage>.printBalance( name: String ) {
    val balance = CompletableDeferred<Long>().let{
        send(GetBalance(it))
        it.await()
    }
    println("$name: balance is $balance")
}

fun main() {
    runBlocking {
        // Zunächst wird sich ein "SendChannel" geholt und mit einer initialBalance versehen.
        @ObsoleteCoroutinesApi
        val manager = accountManager(initialBalance = 3000L)

        // Damit die folgenden Aufrufe nicht blockend sind, wird jeweils ein einzelner launch aufgerufen,
        // in dem dann die suspendig-Functions aufgerufen werden können...
        withContext(Dispatchers.Default) {
            launch { manager.deposit("Client #1", 50)
                     manager.printBalance("Client #1")
            }

            launch { manager.tryWithdraw("Client #2", 100)
                manager.printBalance("Client #2") }
        }

        // Dieser Aufruf läuft blockend im "Hauptprozess und ist damit blockend"
        manager.tryWithdraw("Client #0", 1000)
        manager.printBalance("Client #0")

        // mit dem Close wird der Channel zum Senden an die Actors geschlossen.
        manager.close()
    }
}







