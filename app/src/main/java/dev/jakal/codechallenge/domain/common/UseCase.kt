package dev.jakal.codechallenge.domain.common

import dev.jakal.codechallenge.common.model.Result
import timber.log.Timber

@Suppress("TooGenericExceptionCaught")
abstract class UseCase<in PARAM, RESULT : Any> {

    operator fun invoke(parameters: PARAM? = null): Result<RESULT> {
        return try {
            execute(parameters).let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Timber.d(e)
            Result.Error(e)
        }
    }

    protected abstract fun execute(parameters: PARAM? = null): RESULT
}
