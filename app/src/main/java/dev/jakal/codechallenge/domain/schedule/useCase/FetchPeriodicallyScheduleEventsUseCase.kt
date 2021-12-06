package dev.jakal.codechallenge.domain.schedule.useCase

import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.ScheduleEventsRepository
import dev.jakal.codechallenge.domain.common.FlowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchPeriodicallyScheduleEventsUseCase @Inject constructor(
    private val scheduleEventsRepository: ScheduleEventsRepository,
    appCoroutineDispatchers: AppCoroutineDispatchers
) : FlowUseCase<Unit, Unit>(appCoroutineDispatchers.io) {

    override fun execute(parameters: Unit?): Flow<Result<Unit>> = flow {
        while (true) {
            emit(Result.Loading(true))
            emit(Result.Success(scheduleEventsRepository.fetchScheduleEvents()))
            emit(Result.Loading(false))
            delay(REPEAT_DELAY_MILLIS)
        }
    }

    companion object {
        const val REPEAT_DELAY_MILLIS = 30000L
    }
}
