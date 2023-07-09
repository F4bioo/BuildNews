package com.fappslab.buildnews.libraries.arch.network.exception

import com.google.gson.Gson

interface ApiError {
    fun toHttpThrowable(): HttpThrowable

    companion object {
        inline fun <reified T : ApiError> parser(): (String) -> ApiError = { errorBody ->
            Gson().fromJson(errorBody, T::class.java)
        }
    }
}
