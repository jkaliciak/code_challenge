package dev.jakal.codechallenge.ui.schedules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.domain.schedule.useCase.FetchPeriodicallyScheduleEventsUseCase
import dev.jakal.codechallenge.domain.schedule.useCase.ObserveScheduleEventsUseCase
import dev.jakal.codechallenge.ui.common.extensions.setState
import dev.jakal.codechallenge.ui.common.model.Event
import dev.jakal.codechallenge.ui.common.model.toEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    observeScheduleEventsUseCase: ObserveScheduleEventsUseCase,
    private val fetchPeriodicallyScheduleEventsUseCase: FetchPeriodicallyScheduleEventsUseCase
) : ViewModel() {

    val state = MutableStateFlow(UiState())
    val event = MutableStateFlow<Event<UiEvent>?>(null)

    private var fetchPeriodicallyJob: Job? = null

    init {
        observeScheduleEventsUseCase()
            .onEach { result ->
                when (result) {
                    is Result.Loading -> state.setState { copy(isLoading = result.isLoading) }
                    is Result.Success -> state.setState { copy(scheduleEvents = result.data) }
                    is Result.Error -> handleError(result)
                }
            }
            .launchIn(viewModelScope)

        fetchPeriodicallyScheduleEvents()
    }

    fun fetchPeriodicallyScheduleEvents() {
        fetchPeriodicallyJob?.cancel()
        fetchPeriodicallyJob = fetchPeriodicallyScheduleEventsUseCase()
            .onEach { result ->
                when (result) {
                    is Result.Loading -> state.setState { copy(isLoading = result.isLoading) }
                    is Result.Success -> return@onEach
                    is Result.Error -> handleError(result)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleError(error: Result.Error) {
        if (error.throwable is CancellationException) return

        state.setState { copy(isLoading = false) }
        event.value = UiEvent.Error.General(error.throwable).toEvent()
    }
}
