package com.fappslab.buildnews.libraries.arch.network.exception.extension

import com.fappslab.buildnews.libraries.arch.network.exception.ApiError
import com.fappslab.buildnews.libraries.arch.network.exception.model.HttpThrowable
import com.fappslab.buildnews.libraries.arch.network.exception.model.InternetThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

private const val UNEXPECTED_ERROR_MESSAGE = "Unexpected error, please try again."

private fun HttpException.parseError(errorParser: (String) -> ApiError): Throwable =
    response()?.errorBody()?.string()?.let {
        errorParser(it).toHttpThrowable()
    } ?: HttpThrowable(message = UNEXPECTED_ERROR_MESSAGE, throwable = this)

private fun Throwable.toThrowable(errorParser: (String) -> ApiError): Throwable =
    when (this) {
        is HttpException -> parseError(errorParser)
        is TimeoutException,
        is IOException -> InternetThrowable()

        else -> this
    }

fun <T> Flow<T>.toHttpErrorParse(errorParser: (String) -> ApiError): Flow<T> =
    catch { cause ->
        throw cause.toThrowable(errorParser)
    }
