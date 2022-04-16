package de.tiupe.lambdaasparameter
/**
 * Dies ist ein Beispiel dafür, wie man eine Funktion an eine Klasse übergeben kann, ohne dass diese von dem
 * aufrufenden Modell etwas wissen muss.
 *
 * Als Beispiel sei ein Druckdialog genannt. Dieser soll lediglich den Druck anstoßen, ohne dass man das
 * Modell an ihn übergeben muss.
 *
 * Der Trick ist, dass man an den Dialog eine Funktion übergibt, die die Signatur () -> String hat.
 * Der String soll die URL zu einem Druckdokument sein. Das Modell geht in diesem Fall in die Funktion ein,
 * da die aufrufende Klasse es schon kennt. So bleibt es bei der Abbildung von () -> String
 *
 * Hier ist jetzt eine andere Variante implementiert, der Druckdialog würde in diesem Fall das Model, in dem Fall
 * den Typparaeter T mitbekommen und auswerten. Die beiden skizzierten Wege sind gleichwertig, allerdings finde
 * ich die zweite Variante etwas einleuchtender, das ist aber Geschmacksache.
 *
 * Es geht beides ()  -> String
 * bzw.           (T) -> String
 *
 * */
fun main() {
    // init ruft die Funktionalität auf, daher wird der Aufruf benötigt...
    ClassCallingCallBack()
}

data class DataModel(val nachname: String, val vorname: String)

class ClassCallingCallBack {
    // Wie die Klasse an das Model kommt, ist an dieser Stelle erst einmal egal.
    // Ich möchte sehen, wie man die Funktion aufrufen kann, ohne dass die
    // empfangende Klasse das Datenmodell kennt.
    val model = DataModel("Marx", "Peter")

    val toCall =  ReceivingClassCallBack<DataModel>({model -> "${model.vorname} ${model.nachname}"})

    init {
        toCall.doSomethingDifferent(model)
        toCall.doSomething("Peter wars"){
            "${model.vorname} ${model.nachname}"
        }
    }

}

class ReceivingClassCallBack<T>(val callback: (T) -> String) {
    fun doSomething(otherParameter: String, callback: () -> String ): String {
        val returnString: String =  callback()
        println("Could have been another Parameter: $otherParameter")
        return returnString
    }

    fun doSomethingDifferent(t: T): String{
        val text: String = callback(t)
        println(text)
        return text
    }

}