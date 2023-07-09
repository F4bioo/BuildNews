package com.fappslab.buildnews.data.model

import com.fappslab.buildnews.libraries.arch.network.exception.ApiError
import com.fappslab.buildnews.libraries.arch.network.exception.HttpThrowable
import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("message")
    val message: String?
) : ApiError {

    override fun toHttpThrowable(): HttpThrowable {
        return HttpThrowable(message = message)
    }
}
