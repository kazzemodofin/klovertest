package com.kazeem.spinningwheel.utils

import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

data class ErrorResponse(
    val errorDescription: String, // translated error shown to the user directly from the API
    val causes: Map<String, String> = emptyMap() // errors on specific field on a form
)

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        ResultWrapper<Nothing>()
    data class NetworkError(val cause: Throwable?) : ResultWrapper<Nothing>()
}

suspend fun <T> saferApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Timber.e("IOException - ${throwable.cause}")
                    ResultWrapper.NetworkError(throwable.cause)
                }
                is HttpException -> {
                    Timber.e("Generic Error - ${throwable.cause}")
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    Timber.e("Generic Error - ${throwable.cause}")
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        val gson = GsonBuilder().create()
        throwable.response()?.errorBody()?.byteStream()?.let { inputStream ->
            val inputAsString = inputStream.bufferedReader().use { it.readText() }
            gson.getAdapter(ErrorResponse::class.java).fromJson(inputAsString)
        }
    } catch (exception: Exception) {
        Timber.e("Exception - ${exception.cause}")
        null
    }
}