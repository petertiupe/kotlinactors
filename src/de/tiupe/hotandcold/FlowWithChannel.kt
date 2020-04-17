package de.tiupe.hotandcold

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.channels.produce
/*
fun <T> Flow<T>.buffer(size: Int = 0): Flow<T> = flow {
    coroutineScope {
        val channel = produce<Int>(capacity = size) {
            collect { send(it) }
        }
        channel.consumeEach { emit(it) }
    }
}
*/
