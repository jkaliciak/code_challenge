package dev.jakal.codechallenge.ui.events

import dev.jakal.codechallenge.domain.events.model.VideoEvent

data class UiState(
    val videoEvents: List<VideoEvent> = emptyList(),
    val isLoading: Boolean = false
)
