package dev.jakal.codechallenge

import app.cash.turbine.test
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.domain.events.useCase.FetchVideoEventsUseCase
import dev.jakal.codechallenge.domain.events.useCase.ObserveVideoEventsUseCase
import dev.jakal.codechallenge.ui.events.EventsViewModel
import dev.jakal.codechallenge.ui.events.UiEvent
import dev.jakal.codechallenge.ui.events.UiState
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.ZonedDateTime

@ExperimentalCoroutinesApi
@ExperimentalKotest
class EventsViewModelSpec : BehaviorSpec({

    lateinit var underTest: EventsViewModel

    val observeVideoEventsUseCase: ObserveVideoEventsUseCase = mockk()
    val fetchVideoEventsUseCase: FetchVideoEventsUseCase = mockk()

    beforeSpec {
        clearAllMocks()
    }

    given("EventsViewModel") {

        and("No video events in cache") {
            every { observeVideoEventsUseCase() } returns MutableSharedFlow()

            and("Fetching video events failed") {
                val exception = Exception()
                every { fetchVideoEventsUseCase() } returns flow { throw exception }

                `when`("EventsViewModel initialized") {
                    underTest = EventsViewModel(observeVideoEventsUseCase, fetchVideoEventsUseCase)

                    then("should emit default state") {
                        underTest.state.value shouldBe UiState()
                    }

                    xthen("should emit error event") {
                        underTest.event.value?.peekContent() shouldBe UiEvent.Error.General(
                            exception
                        )
                    }
                }
            }
        }

        and("Video events successfully retrieved from cache") {
            val now = ZonedDateTime.now()
            val videoEvents = listOf(
                mockVideoEvent("1", now),
                mockVideoEvent("2", now),
                mockVideoEvent("3", now),
            )
            every { observeVideoEventsUseCase() } returns MutableStateFlow(
                Result.Success(videoEvents)
            )

            and("Fetching video events failed") {
                val exception = Exception()
                every { fetchVideoEventsUseCase() } returns flow { throw exception }

                `when`("EventsViewModel initialized") {
                    underTest = EventsViewModel(observeVideoEventsUseCase, fetchVideoEventsUseCase)

                    then("should emit state with video events") {
                        underTest.state.test {
                            awaitItem() shouldBe UiState(videoEvents = videoEvents)
                        }
                    }

                    xthen("should emit error event") {
                        underTest.event.value?.peekContent() shouldBe UiEvent.Error.General(
                            exception
                        )
                    }
                }
            }
        }

        and("Video events successfully retrieved from cache and fetched") {
            val now = ZonedDateTime.now()
            val videoEvents = listOf(
                mockVideoEvent("1", now),
                mockVideoEvent("2", now),
                mockVideoEvent("3", now),
            )
            val later = ZonedDateTime.now()
            val fetchedVideoEvents = listOf(
                mockVideoEvent("4", later),
                mockVideoEvent("5", later),
                mockVideoEvent("6", later),
            )
            every { observeVideoEventsUseCase() } returns flow {
                emit(Result.Success(videoEvents))
                emit(Result.Success(fetchedVideoEvents))
            }

            and("Fetching video events succeeded") {
                every { fetchVideoEventsUseCase() } returns flow {
                    Result.Loading<Unit>(true)
                    Result.Success(Unit)
                    Result.Loading<Unit>(false)
                }

                `when`("EventsViewModel initialized") {
                    underTest = EventsViewModel(observeVideoEventsUseCase, fetchVideoEventsUseCase)

                    then("should emit state with new video events") {
                        underTest.state.value shouldBe UiState(
                            videoEvents = fetchedVideoEvents,
                            isLoading = false
                        )
                    }

                    then("should emit no events") {
                        underTest.event.value?.peekContent() shouldBe null
                    }
                }
            }
        }

        `when`("Video event list item clicked") {
            val videoEvent = mockVideoEvent("1", ZonedDateTime.now())
            underTest.onVideoEventClicked(videoEvent)

            then("should emit open video navigation event") {
                underTest.event.value?.peekContent() shouldBe UiEvent.Navigation.OpenVideo(
                    videoEvent
                )
            }
        }
    }
})
