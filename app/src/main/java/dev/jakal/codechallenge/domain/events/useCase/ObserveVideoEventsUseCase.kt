package dev.jakal.codechallenge.domain.events.useCase

import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.VideoEventsRepository
import dev.jakal.codechallenge.domain.common.FlowUseCase
import dev.jakal.codechallenge.domain.events.model.VideoEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveVideoEventsUseCase @Inject constructor(
    private val videoEventsRepository: VideoEventsRepository,
    appCoroutineDispatchers: AppCoroutineDispatchers
) : FlowUseCase<Unit, List<VideoEvent>>(appCoroutineDispatchers.computation) {

    override fun execute(parameters: Unit?): Flow<Result<List<VideoEvent>>> =
        videoEventsRepository.observeVideoEvents()
            .map { list -> Result.Success(list.sortedBy { it.date }) }
}
