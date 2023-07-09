package com.fappslab.buildnews.data.repository

import com.fappslab.buildnews.common.data.source.BuildNewsDataSource
import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.common.domain.repository.BuildNewsRepository
import kotlinx.coroutines.flow.Flow

class BuildNewsRepositoryImpl(
    private val dataSource: BuildNewsDataSource
) : BuildNewsRepository {

    override fun getArticles(): Flow<Articles> =
        dataSource.getArticles()
}
