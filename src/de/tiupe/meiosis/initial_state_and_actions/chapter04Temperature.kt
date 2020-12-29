package de.tiupe.meiosis.initial_state_and_actions

typealias TemperatureHandler = (Temperature) -> Temperature


/* Einheit für die Temperatur */
enum class TempUnit(val unit: String) {
    Celsius("C"), // Enum durch Komma trennen
    Fahrenheit("F")
}

/* Let's build a temperature example with the following initial state
*
*   {
*      temperature: {
*          value: 22,
*          units: "C"
*      }
*   }
*
*  */


/*
* Wie ich es im chapter03 schon gemacht habe, ist das Ziel, Funktionen in den Stream zu schreiben.
* */

data class Temperature(var temperature:Int = 22, var unit: TempUnit = TempUnit.Celsius) {

    var update: Sequence<(TemperatureHandler)> = emptySequence()

    fun addHandler(hdl: TemperatureHandler) {
        this.update = this.update.plus(hdl)
    }


    fun getStatus(): Temperature {
        update.scan(this) {a, b -> b(a) }.last()
        // Die Queue ist abgearbeitet, und kann geleert werden.
        this.update = emptySequence()
        return this
    }

}

fun main() {
    val erhoeheUm3Grad: TemperatureHandler =  { a ->
        a.temperature = a.temperature + 3
        a
    }

    val erniedrigeUm4Grad : TemperatureHandler = { a ->
        a.temperature = a.temperature - 4
        a
    }

    val changeUnits: TemperatureHandler = { a ->
        if(a.unit.equals(TempUnit.Celsius)) {
            a.unit = TempUnit.Fahrenheit
            a.temperature = (a.temperature + 32) * 2
        } else {
            a.unit = TempUnit.Celsius
            a.temperature = (a.temperature - 32) / 2
        }
        a
    }

    val startTemp = Temperature(10, TempUnit.Celsius)
    startTemp.addHandler(erhoeheUm3Grad)
    startTemp.addHandler(erhoeheUm3Grad)
    startTemp.addHandler(erniedrigeUm4Grad)
    startTemp.getStatus()
    println("aktuelle Temperatur ist: ${startTemp.temperature} ${startTemp.unit}")

    startTemp.addHandler { a ->
        a.temperature = a.temperature * 10
        a
    }

    startTemp.getStatus()
    println("Jetzt wirds heiss: ${startTemp.temperature} ${startTemp.unit}")

    startTemp.addHandler(changeUnits)
    startTemp.getStatus()
    println("In Fahrenheit: ${startTemp.temperature}")

}


