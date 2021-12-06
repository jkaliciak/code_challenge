package dev.jakal.codechallenge.ui.common.extensions

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.setState(newState: T.() -> T) {
    this.value = newState(this.value)
}
