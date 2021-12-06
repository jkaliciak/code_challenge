package dev.jakal.codechallenge.domain.schedule.useCase

import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.ScheduleEventsRepository
import dev.jakal.codechallenge.domain.common.FlowUseCase
import dev.jakal.codechallenge.domain.schedule.model.ScheduleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveScheduleEventsUseCase @Inject constructor(
    private val scheduleEventsRepository: ScheduleEventsRepository,
    appCoroutineDispatchers: AppCoroutineDispatchers
) : FlowUseCase<Unit, List<ScheduleEvent>>(appCoroutineDispatchers.computation) {

    override fun execute(parameters: Unit?): Flow<Result<List<ScheduleEvent>>> =
        scheduleEventsRepository.observeScheduleEvents()
            .map { list -> Result.Success(list.sortedBy { it.date }) }
}
