package dev.jakal.codechallenge.ui.schedules

import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent

data class UiState(
    val scheduleEvents: List<ScheduleEvent> = emptyList(),
    val isLoading: Boolean = false
)
