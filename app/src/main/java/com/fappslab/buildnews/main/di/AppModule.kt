package com.fappslab.buildnews.main.di

import com.fappslab.buildnews.BuildConfig
import com.fappslab.buildnews.common.data.network.retrofit.HttpClientImpl
import com.fappslab.buildnews.common.data.network.retrofit.RetrofitClient
import com.fappslab.buildnews.common.domain.usecase.GetArticlesUseCase
import com.fappslab.buildnews.common.domain.usecase.GetFormattedDateUseCase
import com.fappslab.buildnews.data.api.network.ApiKeyInterceptor
import com.fappslab.buildnews.libraries.arch.koinload.KoinLoad
import com.fappslab.buildnews.libraries.arch.network.client.HttpClient
import com.fappslab.buildnews.libraries.arch.network.interceptor.HeaderInterceptor
import com.fappslab.buildnews.main.domain.provider.UseCaseProvider
import com.fappslab.buildnews.main.presentation.viewmodel.MainViewModel
import okhttp3.Interceptor
import org.koin.androidx.viewmodel.dsl.viewModel
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
                baseUrl = BuildConfig.BASE_URL,
                interceptors = get()
            ).create()
        }

        single<HttpClient> {
            HttpClientImpl(retrofit = get())
        }
    }

    override val presentationModule: Module = module {
        viewModel {
            MainViewModel(
                provider = UseCaseProvider(
                    getFormattedDateUseCase = GetFormattedDateUseCase(),
                    getArticlesUseCase = GetArticlesUseCase(
                        repository = get()
                    )
                )
            )
        }
    }
}
