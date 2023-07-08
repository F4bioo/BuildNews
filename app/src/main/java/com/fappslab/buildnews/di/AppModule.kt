package com.fappslab.buildnews.di


import com.fappslab.buildnews.BuildConfig
import com.fappslab.buildnews.data.network.retrofit.HttpClientImpl
import com.fappslab.buildnews.data.network.retrofit.RetrofitClient
import com.fappslab.buildnews.libraries.arch.koinload.KoinLoad
import com.fappslab.buildnews.libraries.arch.koinload.KoinQualifier
import com.fappslab.buildnews.libraries.arch.network.client.HttpClient
import com.fappslab.buildnews.libraries.arch.network.interceptor.ApiKeyInterceptor
import com.fappslab.buildnews.libraries.arch.network.interceptor.HeaderInterceptor
import okhttp3.Interceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object RetrofitInterceptorQualifier : KoinQualifier

object AppModule : KoinLoad() {

    override val dataModule: Module = module {
        single(qualifier = RetrofitInterceptorQualifier) {
            listOf<Interceptor>(
                ApiKeyInterceptor(apiKey = BuildConfig.API_KEY),
                HeaderInterceptor()
            )
        }

        single<Retrofit> {
            RetrofitClient(
                baseUrl = BuildConfig.BASE_URL,
                interceptors = get()
            ).create()
        }

        single<HttpClient> {
            HttpClientImpl(retrofit = get())
        }
    }
}
