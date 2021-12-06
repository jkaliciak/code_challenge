package dev.jakal.codechallenge

import app.cash.turbine.test
import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.VideoEventsRepository
import dev.jakal.codechallenge.domain.events.useCase.ObserveVideoEventsUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.threeten.bp.ZonedDateTime

class ObserveVideoEventsUseCaseSpec : BehaviorSpec({

    lateinit var underTest: ObserveVideoEventsUseCase

    val repository: VideoEventsRepository = mockk()
    val dispatcher = TestCoroutineDispatcher()
    val appCoroutinesDispatchers = AppCoroutineDispatchers(
        io = dispatcher,
        computation = dispatcher,
        main = dispatcher,
    )

    beforeSpec {
        clearAllMocks()
    }

    given("ObserveVideoEventsUseCase") {
        underTest = ObserveVideoEventsUseCase(repository, appCoroutinesDispatchers)

        and("Repository does not emit") {
            every { repository.observeVideoEvents() } returns MutableSharedFlow()

            `when`("use case executed") {

                then("should not emit") {
                    underTest().test {
                        expectNoEvents()
                    }
                }
            }
        }

        and("Repository emits video events") {
            val now = ZonedDateTime.now()
            val videoEvents = listOf(
                mockVideoEvent("1", now.plusDays(1)),
                mockVideoEvent("3", now.plusDays(2)),
                mockVideoEvent("2", now),
                mockVideoEvent("4", now.minusYears(1))
            )
            every { repository.observeVideoEvents() } returns MutableStateFlow(
                videoEvents
            )

            `when`("use case executed") {

                then("should emit success with video events sorted by date ascending") {
                    val expected = videoEvents.sortedBy { it.date }
                    underTest().test {
                        (awaitItem() as Result.Success).data shouldBe expected
                    }
                }
            }
        }

        and("Repository emits exception") {
            val exception = Exception()
            every { repository.observeVideoEvents() } returns flow { throw exception }

            `when`("use case executed") {

                then("should emit error") {
                    underTest().test {
                        awaitItem() shouldBe Result.Error(exception)
                        awaitComplete()
                    }
                }
            }
        }
    }
})
