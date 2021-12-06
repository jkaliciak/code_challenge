package dev.jakal.codechallenge

import app.cash.turbine.test
import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.VideoEventsRepository
import dev.jakal.codechallenge.domain.events.useCase.FetchVideoEventsUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.test.TestCoroutineDispatcher

class FetchVideoEventsUseCaseSpec : BehaviorSpec({

    lateinit var underTest: FetchVideoEventsUseCase

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

    given("FetchVideoEventsUseCase") {
        underTest = FetchVideoEventsUseCase(repository, appCoroutinesDispatchers)

        and("Repository fetches events successfully") {
            coEvery { repository.fetchVideoEvents() } just Runs

            `when`("use case executed") {
                then("should emit loading, success and not loading") {
                    underTest().test {
                        awaitItem() shouldBe Result.Loading(true)
                        awaitItem() shouldBe Result.Success(Unit)
                        awaitItem() shouldBe Result.Loading(false)
                        awaitComplete()
                    }
                }
            }
        }

        and("Repository emits exception") {
            val exception = Exception()
            coEvery { repository.fetchVideoEvents() } throws exception

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
