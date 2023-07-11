package com.fappslab.buildnews.di


import com.fappslab.buildnews.common.domain.repository.BuildNewsRepository
import com.fappslab.buildnews.data.api.NYTimesApiService
import com.fappslab.buildnews.data.repository.BuildNewsRepositoryImpl
import com.fappslab.buildnews.data.source.BuildNewsDataSourceImpl
import com.fappslab.buildnews.libraries.arch.koinload.KoinLoad
import com.fappslab.buildnews.libraries.arch.network.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

object FlavorModule : KoinLoad() {

    override val dataModule: Module = module {
        factory<BuildNewsRepository> {
            BuildNewsRepositoryImpl(
                dataSource = BuildNewsDataSourceImpl(
                    service = get<HttpClient>().create(
                        clazz = NYTimesApiService::class.java
                    )
                )
            )
        }
    }
}
