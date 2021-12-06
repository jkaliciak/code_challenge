package dev.jakal.codechallenge

import app.cash.turbine.test
import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.ScheduleEventsRepository
import dev.jakal.codechallenge.domain.schedule.useCase.FetchPeriodicallyScheduleEventsUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

class FetchPeriodicallyScheduleEventsUseCaseSpec : BehaviorSpec({

    lateinit var underTest: FetchPeriodicallyScheduleEventsUseCase

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

    given("FetchPeriodicallyScheduleEventsUseCase") {
        underTest = FetchPeriodicallyScheduleEventsUseCase(repository, appCoroutinesDispatchers)

        and("Repository fetches events successfully once") {
            val exception = Exception()
            coEvery { repository.fetchScheduleEvents() } just Runs andThenThrows exception

            `when`("use case executed") {

                then("should emit loading, success and not loading") {
                    underTest().test {
                        awaitItem() shouldBe Result.Loading(true)
                        awaitItem() shouldBe Result.Success(Unit)
                        awaitItem() shouldBe Result.Loading(false)
                        cancel()
                    }
                }
            }
        }

        and("Repository fetches events successfully once and than throws exception") {
            val exception = Exception()
            coEvery { repository.fetchScheduleEvents() } just Runs andThenThrows exception

            `when`("use case executed") {

                then("should emit loading, success, not loading, loading and error") {
                    dispatcher.runBlockingTest {
                        underTest().test(timeoutMs = 30000L) {
                            awaitItem() shouldBe Result.Loading(true)
                            awaitItem() shouldBe Result.Success(Unit)
                            awaitItem() shouldBe Result.Loading(false)
                            awaitItem() shouldBe Result.Loading(true)
                            dispatcher.advanceTimeBy(30000L)
                            awaitItem() shouldBe Result.Error(exception)
                            cancel()
                        }
                    }
                }
            }
        }

        and("Repository throws exception") {
            val exception = Exception()
            coEvery { repository.fetchScheduleEvents() } throws exception

            `when`("use case executed") {

                then("should emit loading and error") {
                    underTest().test {
                        awaitItem() shouldBe Result.Loading(true)
                        awaitItem() shouldBe Result.Error(exception)
                        awaitComplete()
                    }
                }
            }
        }
    }
})
