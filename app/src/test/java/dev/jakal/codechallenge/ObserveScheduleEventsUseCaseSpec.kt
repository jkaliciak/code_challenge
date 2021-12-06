package dev.jakal.codechallenge

import app.cash.turbine.test
import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.ScheduleEventsRepository
import dev.jakal.codechallenge.domain.schedule.useCase.ObserveScheduleEventsUseCase
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

class ObserveScheduleEventsUseCaseSpec : BehaviorSpec({

    lateinit var underTest: ObserveScheduleEventsUseCase

    val repository: ScheduleEventsRepository = mockk()
    val dispatcher = TestCoroutineDispatcher()
    val appCoroutinesDispatchers = AppCoroutineDispatchers(
        io = dispatcher,
        computation = dispatcher,
        main = dispatcher,
    )

    beforeSpec {
        clearAllMocks()
    }

    given("ObserveScheduleEventsUseCase") {
        underTest = ObserveScheduleEventsUseCase(repository, appCoroutinesDispatchers)

        and("Repository does not emit") {
            every { repository.observeScheduleEvents() } returns MutableSharedFlow()


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
                mockScheduleEvent("1", now.plusDays(4)),
                mockScheduleEvent("2", now),
                mockScheduleEvent("3", now.plusDays(7)),
                mockScheduleEvent("4", now.minusYears(2))
            )
            every { repository.observeScheduleEvents() } returns MutableStateFlow(
                videoEvents
            )

            `when`("use case executed") {
                then("should emit success with schedule events sorted by date ascending") {
                    val expected = videoEvents.sortedBy { it.date }
                    underTest().test {
                        (awaitItem() as Result.Success).data shouldBe expected
                    }
                }
            }
        }

        and("Repository emits exception") {
            val exception = Exception()
            every { repository.observeScheduleEvents() } returns flow { throw exception }

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
