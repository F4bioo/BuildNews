package com.fappslab.buildnews.data.model

import com.fappslab.buildnews.libraries.arch.network.exception.ApiError
import com.fappslab.buildnews.libraries.arch.network.exception.model.HttpThrowable
import com.google.gson.annotations.SerializedName

data class ArticlesErrorResponse(
    @SerializedName("fault")
    val fault: FaultResponse
) : ApiError {

    data class FaultResponse(
        @SerializedName("faultstring")
        val message: String?
    )

    override fun toHttpThrowable(): HttpThrowable {
        return HttpThrowable(message = fault.message)
    }
}
