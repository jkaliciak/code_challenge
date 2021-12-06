package dev.jakal.codechallenge.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.domain.events.model.VideoEvent
import dev.jakal.codechallenge.domain.events.useCase.FetchVideoEventsUseCase
import dev.jakal.codechallenge.domain.events.useCase.ObserveVideoEventsUseCase
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
class EventsViewModel @Inject constructor(
    observeVideoEventsUseCase: ObserveVideoEventsUseCase,
    private val fetchVideoEventsUseCase: FetchVideoEventsUseCase
) : ViewModel() {

    val state = MutableStateFlow(UiState())
    val event = MutableStateFlow<Event<UiEvent>?>(null)

    private var fetchVideoEventsJob: Job? = null

    init {
        observeVideoEventsUseCase()
            .onEach { result ->
                when (result) {
                    is Result.Loading -> state.setState { copy(isLoading = result.isLoading) }
                    is Result.Success -> state.setState { copy(videoEvents = result.data) }
                    is Result.Error -> handleError(result)
                }
            }
            .launchIn(viewModelScope)

        fetchVideoEvents()
    }

    fun fetchVideoEvents() {
        fetchVideoEventsJob?.let { job ->
            if (job.isActive) return
        }

        fetchVideoEventsJob = fetchVideoEventsUseCase()
            .onEach { result ->
                when (result) {
                    is Result.Loading -> state.setState { copy(isLoading = result.isLoading) }
                    is Result.Success -> return@onEach
                    is Result.Error -> handleError(result)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onVideoEventClicked(videoEvent: VideoEvent) {
        event.value = UiEvent.Navigation.OpenVideo(videoEvent).toEvent()
    }

    private fun handleError(error: Result.Error) {
        if (error.throwable is CancellationException) return

        state.setState { copy(isLoading = false) }
        event.value = UiEvent.Error.General(error.throwable).toEvent()
    }
}
