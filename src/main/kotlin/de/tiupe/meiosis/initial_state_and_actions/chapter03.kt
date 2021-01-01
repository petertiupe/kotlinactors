package de.tiupe.meiosis.initial_state_and_actions

/*
* Ziel ist es, den folgenden Code nach Kotlin zu transformieren.
*
* var app = {
  initial: {
    value: 0
  },
  Actions: function (update) {
    return {
      increment: function () {
        update(1);
      },
      decrement: function () {
        update(-1);
      }
    };
  }
};

var update = flyd.stream();
var states = flyd.scan(
  function (state, increment) {
    state.value = state.value + increment;
    return state;
  },
  app.initial,
  update
);

// eslint-disable-next-line no-unused-vars
var actions = app.Actions(update);
states.map(function (state) {
  document.write("<pre>" + JSON.stringify(state) + "</pre>");
});

* */

/*
var app = {
    initial: {
        value: 0
}
Der Status lässt sich am besten in einer Data-class abbilden. */

data class AppState(var state: Int = 0){

    private var actions = emptySequence<(Int) -> Int>()

    fun addAction(action: (Int) -> Int){
        this.actions = this.actions.plus(action)
    }

    fun getState1(): Int = actions.scan(state) { a, b -> b(a) }.last()

}

fun main(){
    val initialState = AppState(0)
    initialState.addAction {a -> a + 3}
    initialState.addAction { a -> a * 4}
    println("Status ist: ${initialState.getState1()} ")
    initialState.addAction { a -> a - 2 }
    println("${initialState.getState1()}")

}