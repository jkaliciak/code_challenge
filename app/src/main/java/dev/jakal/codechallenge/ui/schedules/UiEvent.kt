package dev.jakal.codechallenge.ui.schedules

sealed class UiEvent {

    sealed class Error : UiEvent() {

        data class General(val exception: Throwable) : Error()
    }
}
