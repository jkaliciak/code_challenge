package dev.jakal.codechallenge.domain.common

import dev.jakal.codechallenge.common.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

@Suppress("TooGenericExceptionCaught")
abstract class SuspendUseCase<in PARAM, RESULT : Any>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: PARAM? = null): Result<RESULT> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    Result.Success(it)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            Result.Error(e)
        }
    }

    protected abstract suspend fun execute(parameters: PARAM? = null): RESULT
}
