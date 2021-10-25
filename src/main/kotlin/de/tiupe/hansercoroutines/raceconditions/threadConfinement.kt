package de.tiupe.hansercoroutines.raceconditions

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*

fun main(){
    runBlocking {
        repeat(1000) {
            go()
        }
    }
}

fun go() = runBlocking {
    val account = BankAccount()

    coroutineScope {
        launch(Dispatchers.Default) {
            repeat(10000) { account.addMoney() }
        }

        launch(Dispatchers.Default) {
            repeat(10000) { account.addMoney() }
        }
    }

    println("Gespart wurden ${account.money} Euro")
}

//val moneyThreadContext = newSingleThreadContext("MoneyThreadContext")

class BankAccount {
    var money = 0
        private set
        get() = field // ACHTUNG: Nicht threadsicher!

    suspend fun addMoney() {
  //      withContext(moneyThreadContext) {
            money++
  //      }
    }
}

