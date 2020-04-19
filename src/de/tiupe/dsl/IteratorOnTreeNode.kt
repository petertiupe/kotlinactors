package de.tiupe.dsl

/**
 * In der Tree-Node - Klasse habe ich für dieses Beispiel die folgende Zeile hinzugefügt:
 * operator fun iterator() = _children.iterator()
 *
 * Damit hat die Tree-Node-Klasse einen Iterator und man kann hier eine for-Schleife verwenden.
 */

fun main(){
    val root = TreeNode("Root")
    root += "Child 1"
    root += "Child 2"
    // Dem Knoten 1 werden Kinder hinzugefügt
    root[1] += "Child 2a"
    root[1] += "Child 2b"


    // Jetzt die Ausgabe für alle Kinder von root[1]:
    // Die for-Schleife funktionert nur, weil die
    // operator-Function iterator in dem TreeNode
    // vorhanden ist und für dieses Beispeil ergänzt wurde.
    // Die Ausgabe des Programms ist.
    //      Child 2a
    //      Child 2b

    for(b in root[1]){
        println(b.data)
    }

}