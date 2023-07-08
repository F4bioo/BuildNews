package com.fappslab.buildnews.data.network.retrofit

import com.fappslab.buildnews.libraries.arch.network.client.HttpClient
import retrofit2.Retrofit

class HttpClientImpl(
    private val retrofit: Retrofit
) : HttpClient {

    override fun <T> create(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}
