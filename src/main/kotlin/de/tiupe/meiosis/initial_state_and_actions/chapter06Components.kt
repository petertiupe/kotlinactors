package de.tiupe.meiosis.initial_state_and_actions

typealias WeatherConditionHandler = (WeatherCondition) -> WeatherCondition

/*
Uses Temperature-Data-Class from Chapter 04 to have two different temperatures
one for the air and one for the water.
*/

enum class SkyConditions() {
    Sunny(), // Enum durch Komma trennen
    Cloudy(),
    Rain()
}

data class WeatherCondition(var precipitation: Boolean = false, var sky: SkyConditions = SkyConditions.Sunny ) {
    var update: Sequence<(WeatherConditionHandler)> = emptySequence()

    fun addHandler(hdl: WeatherConditionHandler) {
        this.update = this.update.plus(hdl)
    }


    fun getStatus(): WeatherCondition {
        update.scan(this) {a, b -> b(a) }.last()
        // Die Queue ist abgearbeitet, und kann geleert werden.
        this.update = emptySequence()
        return this
    }
}

data class Sammelobjekt(var wc: WeatherCondition, var tempList: List<Temperature> = emptyList()) {

    // aktualisiert den Status für jedes der enthaltenen Objekte...
    fun getStatus(): Sammelobjekt {
        wc.getStatus()
        for(a in tempList) a.getStatus()
        return this
    }

}

fun main() {
    val airTemp = Temperature()
    val waterTemp = Temperature()

    val erhoeheUm3Grad: TemperatureHandler =  {
        it.temperature = it.temperature + 3
        it
    }

    val erniedrigeUm4Grad : TemperatureHandler = {
        it.temperature = it.temperature - 4
        it
    }

    val store = Sammelobjekt(WeatherCondition(), listOf(airTemp, waterTemp))

    // Hier muss man wissen, welche Temp angefasst wird, das ist noch nicht so einfach zu handhaben...
    store.tempList[0].addHandler(erhoeheUm3Grad)
    store.tempList[1].addHandler(erniedrigeUm4Grad)
    store.wc.addHandler { a ->
        a.precipitation=true
        a.sky = SkyConditions.Rain
        a
    }

    store.getStatus()

    println("Die Wetterconditions sind: ")
    println("Niederschlag: ${store.wc.precipitation}")
    println("Himmel: ${store.wc.sky}")
    println("Temperatur am Boden: ${store.tempList[0].temperature} Grad ${store.tempList[0].unit}")
    println("Wassertemperatur: ${store.tempList[1].temperature} Grad ${store.tempList[1].unit}")

}
