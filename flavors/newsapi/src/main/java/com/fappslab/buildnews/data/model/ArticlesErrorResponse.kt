package com.fappslab.buildnews.data.model

import com.fappslab.buildnews.libraries.arch.network.exception.ApiError
import com.fappslab.buildnews.libraries.arch.network.exception.model.HttpThrowable
import com.google.gson.annotations.SerializedName

data class ArticlesErrorResponse(
    @SerializedName("message")
    val message: String?
) : ApiError {

    override fun toHttpThrowable(): HttpThrowable {
        return HttpThrowable(message = message)
    }
}
