package de.tiupe.meiosis.initial_state_and_actions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.scan

typealias WeatherConditionFlowHandler = (WeatherConditionFlow) -> WeatherConditionFlow

@ExperimentalCoroutinesApi
data class WeatherConditionFlow(var precipitation: Boolean = false, var sky: SkyConditions = SkyConditions.Sunny ) {

    var flowW: Flow<WeatherConditionFlowHandler> = emptyFlow<WeatherConditionFlowHandler>()



    // Ein Flow kennt keine plus Funktion, man kann nicht einfach etwas hinzufügen ...
    // Das braucht eine Lösung...
   fun addHandler(hdl: WeatherConditionFlowHandler) {
        //this.flowW = this.flowW.
    }


    fun getStatus(): WeatherConditionFlow {
        // Beim Flow gibt es nicht die Möglichkeit last aufzurufen, um an den Wert des Flows zu kommen.
        // Hier wird der Unterschied zwischen Sequence und Flow sehr deutlich. Die Sequence ist synchron,
        // der Flow ist asynchron ...
        // flowW.scan(this) {a, b -> b(a) }.last()
        flowW.scan(this){a, b -> b(a)}
        // Die Queue ist abgearbeitet, und kann geleert werden.
        this.flowW = emptyFlow<WeatherConditionFlowHandler>()
        return this
    }
}