package com.fappslab.buildnews.libraries.arch.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor(
    private val queryParam: String,
    private val apiKey: String,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url
        val newUrl = requestUrl.newBuilder()
            .addQueryParameter(queryParam, apiKey)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
