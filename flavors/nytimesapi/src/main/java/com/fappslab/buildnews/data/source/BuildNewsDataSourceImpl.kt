package com.fappslab.buildnews.data.source

import com.fappslab.buildnews.common.data.source.BuildNewsDataSource
import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.data.model.ArticlesErrorResponse
import com.fappslab.buildnews.data.api.NYTimesApiService
import com.fappslab.buildnews.libraries.arch.network.exception.ApiError
import com.fappslab.buildnews.libraries.arch.network.exception.extension.toHttpErrorParse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BuildNewsDataSourceImpl(
    private val service: NYTimesApiService
) : BuildNewsDataSource {

    override fun getArticles(): Flow<Articles> = flow {
        emit(service.getArticles().toArticles())
    }.toHttpErrorParse(ApiError.parser<ArticlesErrorResponse>())
}
