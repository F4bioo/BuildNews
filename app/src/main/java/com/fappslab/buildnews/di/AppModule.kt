package com.fappslab.buildnews.di

import com.fappslab.buildnews.BuildConfig
import com.fappslab.buildnews.common.data.network.retrofit.HttpClientImpl
import com.fappslab.buildnews.common.data.network.retrofit.RetrofitClient
import com.fappslab.buildnews.data.api.network.ApiKeyInterceptor
import com.fappslab.buildnews.libraries.arch.koinload.KoinLoad
import com.fappslab.buildnews.libraries.arch.network.client.HttpClient
import com.fappslab.buildnews.libraries.arch.network.interceptor.HeaderInterceptor
import okhttp3.Interceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object AppModule : KoinLoad() {

    override val dataModule: Module = module {
        single<List<Interceptor>> {
            listOf<Interceptor>(
                HeaderInterceptor(),
                ApiKeyInterceptor(BuildConfig.API_KEY)
            )
        }

        single<Retrofit> {
            RetrofitClient(
                baseUrl = BuildConfig.BUILD_TYPE,
                interceptors = get()
            ).create()
        }

        single<HttpClient> {
            HttpClientImpl(retrofit = get())
        }
    }
}
