package de.tiupe.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Dies ist eine Datei, die den folgenden Medium-Artikel aufgreift:
 *
 *  [Link Medium](https://medium.com/swlh/channels-in-kotlin-part-one-594ba12dcb5a)
 *
 * Ein Channel ist ein Element um Datenströme zwischen Coroutinen auszutauschen.
 * Es gibt immer eine Quelle und eine Senke oder in den Interfaces ausgedrück,
 *
 *      SendChannel
 * und
 *      ReceiveChannel
 *
 * Beide Funktionen send und receive sind suspending auf einem Channel und außerdem wird
 * mit dem oben gesagten klar, dass es keinen Sinn macht, beide in derselben Coroutine zu nutzen.
 *
 * Es gibt folgende Channel Typen:
 *
 *      Channel.RENDEZVOUS
 *      Channel.BUFFERED        --> wenn im Buffer Platz ist, unterbricht der channel nicht und sendet weiter
 *      Channel.UNLIMITED
 *      Channel.CONFLATED
 *
 *  Ein wichtiger Hinweis: Channel "broadcasten" nicht. Wenn ein Receiver einmal eienen Wert von einem Channel
 *  angenommen hat, wird der Wert vom Channel genommen und steht für andere Receiver nicht mehr zur Verfügung.
 *
 *
 */

fun main(){

    /**
     * Der folgende Code-Block endet nie, da der send-call suspending ist und
     * nur durch den Aufruf eines "receives" wieder zum Leben erweckt werden kann.
     * Da hier der receive aber nie erreicht wird, ist der Dead-Lock perfekt.
     *
     * Den Code kommentiere ich daher aus.
     */

    /* THIS IS A DEADLOCK */
    /* ================== */

    /* RENDEVOUS-Channel wird initiiert, indem eine Größe (hier 5) BEIM Konstruktor angegeben wird.
    *            so terminiert das Programm
    * */

    /*
        runBlocking {

        val channel = Channel<String>()
        channel.send("value")
        channel.receive()
        }
     */
    /* THIS IS THE END OF THE DEADLOCK */
    /* =============================== */

    /* BUFFERED-Channel wird initiiert, indem eine Größe (hier 5) BEIM Konstruktor angegeben wird.
    *           so terminiert das Programm
    * */
    runBlocking {
        val channel = Channel<String>(5)
        launch{
            channel.send("value")
            channel.receive()
        }
    }


    /* UNLIMITED-Channel wird initiiert, indem der Typ im Konstruktor angegeben wird.
    *
    * Ein UNLIMITED-Channel funktioniert so, dass der Sender niemals suspended.
    * Er wird oftmals verwendet, wenn manchmal viele Werte und manchmal gar keine gesendet werden.
    *
    * */

    /*
    runBlocking {
        /* RECEIVING-Channel reagiert auf Track-Events */
        val channel = Channel<Event>(Channel.UNLIMITED)
        launch {
            onTrackEvent {
                channel.send(it)
            }
        }

     */
        /* SENDING-Channel sendet dauerhauft ohne Pause */

        /*
        launch {
            while(!channel.isClosedForReceive) {
                repeat(25) {
                    batch.add(channel.receive())
                }
                batch.send()
            }
        }
    }
    */
    /* CONFLATED-Channel wird initiiert, indem wieder der Typ im Konstruktor angegeben wird.
    *
    * Ein CONFLATED-Channel funktioniert so, dass immer nur der letzte Wert, also der aktuellste
    * von dem Channel verarbeitet wird.
    *
    * Z.B. bei Werten, die in einer Oberfläche angezeigt werden sollen, interessiert in der Regel nur der
    * letzte, also kann man den einfach nehmen.
    *
    * Es können auch unterschiedliche Typen von Channels Daten austauschen.
    * Ein UNLIMITED-Channel kann z.B. an einen CONFLATED-Channel senden.
    * */




}