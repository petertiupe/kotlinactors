package de.tiupe.dsl

/**
 * Die Klasse TreeNode wird als Beispiel für die Indexing-Operators verwendet.
 *
 * */
class TreeNode<T>(var data: T) {
    private val _children = arrayListOf<TreeNode<T>>()


    var parent: TreeNode<T>? = null
        // Es gibt einen Setter für den Parent, der private ist, weil man den
        // Parent unter eigener Kontrolle halten will. Der Parent wird immer dann
        // gesetzt, wenn ein node hinzugefügt wird, dann ist der Parent nämlich klar.
        private set

    operator fun plusAssign(data: T) {
        val node = TreeNode(data)
        _children += node
        node.parent = this
    }

    operator fun minusAssign(data: T) {
        val index = _children.indexOfFirst { it.data == data }
        if (index < 0) return
        val node = _children.removeAt(index)
        node.parent = null
    }

  operator fun iterator() = _children.iterator()



    /*
    * Hier ist die get-Funktion, die für dien IndexingOperator entscheidend ist.
    * Sie wird aufgerufen, wenn auf den Index via [] zugegriffen wird.
    * */
    operator fun get(index: Int) = _children[index]

    /*
    * Und hier dasselbe noch einmal für die set-Funktion, wenn die [] auf der
    * linken Seite der Zuweisung stehen.
    * */
    operator fun set(index: Int, node: TreeNode<T>) {
        node.parent?._children?.remove(node)
        node.parent = this
        _children[index].parent = null
        _children[index] = node
    }
}



/**
 * Die main-Funktion zeigt die Aufrufe über die Indexing-Operators
 * */
fun main() {
    val root = TreeNode("Root")
    root += "Child 1"
    root += "Child 2"
    // Hier wird die get-Funktion über den Index benutzt
    println(root[1].data) // Child 2
    // Hier hat man über die eckigen Klammern einen Aufruf der set-Funktion
    // des TreeNodes.
    root[0] = TreeNode("Child 3")
    println(root[0].data) // Child 3
}
