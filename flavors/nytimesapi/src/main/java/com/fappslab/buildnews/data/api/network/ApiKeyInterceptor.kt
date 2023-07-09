package com.fappslab.buildnews.data.api.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val PATH = "?api-key="

class ApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url
        val newUrl = requestUrl.newBuilder()
            .addPathSegment("$PATH$apiKey")
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
