package de.tiupe.`object`

/**
 * Eine Enumeration ist in Kotlin eine Klasse, ergänzt um das
 * Schlüsselwort enum
 *
 * Einer der großen Vorteile von Enumerations ist, dass man sie
 * in when-Expressions so gut nutzen und auf den "else"-Zweig verzichten
 * kann.
 * Daneben können Aufzählungen eigene abstrakte Funktionen enthalten.
 *
 * Enumerations kann man als abgschlossene Klassen betrachten, man
 * kann von außen keine neuen Instanzen einer Enumeration anlegen.
 *
 * Das Programm kompiliert, aber die Ampel funktioniert nicht richtig.
 * Da die Switch-Funktionen sich selbst aufrufen, terminieren sie nicht und
 * es kommt zu einem Stackoverflow.
 *
 * Hier geht es um den Aufbau einer Enumeration und darum, dass diese eigene Funktionen
 * besitzen können.
 *
 * ACHTUNG: Man übersieht es schnell, in der eunum-Klasse sind die einzelnen Werte durch
 *          ein Komma getrennt und die abstrakte Funktion ist dann noch einmal durch ein
 *          Semikolon getrennt. Das ist die erste Stelle, an der ein Semikolon notwendig ist.
 *
 * */

enum class Ampelsignal(val farbe: String, var anAusState: Boolean) {

    ROT("ROT", false) {
        override fun switchState() {
            if(ROT.anAusState && GELB.anAusState){
                ROT.switchState()
                GELB.switchState()
                GRUEN.switchState()
            } else if(!ROT.anAusState && GELB.anAusState) {
                ROT.switchState()
                GELB.switchState()
            }
        }
    },

    GELB("GELB", false){
        override fun switchState() {
            if(ROT.anAusState && !GELB.anAusState){
                GELB.switchState()
            }
        }
    },

    GRUEN("GRUEN", false){
        override fun switchState() {
            if(GRUEN.anAusState){
                GELB.switchState()
                GRUEN.switchState()
            } else {
                ROT.switchState()
            }
        }
    };

    fun schalteAufGrün(){
        ROT.switchState()
        GELB.switchState()
        GRUEN.switchState()
    }

    abstract fun switchState()
}

class Ampel(val rot: Ampelsignal=Ampelsignal.ROT, val gelb: Ampelsignal = Ampelsignal.GELB, val gruen: Ampelsignal = Ampelsignal.GRUEN){
    init{
        // Zur Sicherheit leuchtet die Ampel immer rot, dann kann man nicht falsch losfahren :-)
        rot.anAusState = true
    }

    fun getAmpelState(){
        println("Rot ist ${this.rot.anAusState}")
        println("Gelb ist ${this.gelb.anAusState}")
        println("Gruen ist ${this.gruen.anAusState}")
    }

}

fun main(){
    // Eine Instanz einer Enumeration kann man nicht ausserhalb der Klasse enum erzeugen.
    // Die folgende Zeile kompiliert nicht.
    // val rot: Ampelsignal = Ampelsignal("Rot")

    val ampel = Ampel()
    ampel.getAmpelState()

    ampel.gelb.switchState()
    ampel.getAmpelState()

    ampel.rot.switchState()
    ampel.getAmpelState()

}