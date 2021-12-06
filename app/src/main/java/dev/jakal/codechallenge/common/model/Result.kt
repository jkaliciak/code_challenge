package dev.jakal.codechallenge.common.model

sealed class Result<out T : Any> {

    data class Loading<out T : Any>(val isLoading: Boolean) : Result<T>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading[isLoading=$isLoading]"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
        }
    }
}

fun <T : Any> Result<T>.onError(block: (Result.Error) -> Unit) {
    if (this is Result.Error) block(this)
}
