package dev.jakal.codechallenge.domain.common

import dev.jakal.codechallenge.common.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

abstract class FlowUseCase<in PARAM, RESULT : Any>(private val coroutineDispatcher: CoroutineDispatcher) {

    open operator fun invoke(parameters: PARAM? = null): Flow<Result<RESULT>> {
        return execute(parameters)
            .catch { e ->
                run {
                    Timber.d(e)
                    emit(Result.Error(e))
                }
            }
            .flowOn(coroutineDispatcher)
    }

    protected abstract fun execute(parameters: PARAM? = null): Flow<Result<RESULT>>
}
