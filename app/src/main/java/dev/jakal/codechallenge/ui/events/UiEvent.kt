package dev.jakal.codechallenge.ui.events

import dev.jakal.codechallenge.domain.events.model.VideoEvent

sealed class UiEvent {

    sealed class Error : UiEvent() {

        data class General(val exception: Throwable) : Error()
    }

    sealed class Navigation : UiEvent() {

        data class OpenVideo(val videoEvent: VideoEvent) : Navigation()
    }
}
