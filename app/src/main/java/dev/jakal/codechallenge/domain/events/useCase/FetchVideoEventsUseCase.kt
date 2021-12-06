package dev.jakal.codechallenge.domain.events.useCase

import dev.jakal.codechallenge.common.AppCoroutineDispatchers
import dev.jakal.codechallenge.common.model.Result
import dev.jakal.codechallenge.common.repository.VideoEventsRepository
import dev.jakal.codechallenge.domain.common.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchVideoEventsUseCase @Inject constructor(
    private val videoEventsRepository: VideoEventsRepository,
    appCoroutineDispatchers: AppCoroutineDispatchers
) : FlowUseCase<Unit, Unit>(appCoroutineDispatchers.io) {

    override fun execute(parameters: Unit?): Flow<Result<Unit>> = flow {
        emit(Result.Loading(true))
        emit(Result.Success(videoEventsRepository.fetchVideoEvents()))
        emit(Result.Loading(false))
    }
}
