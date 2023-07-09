package com.fappslab.buildnews.common.data.source

import com.fappslab.buildnews.common.domain.model.Articles
import kotlinx.coroutines.flow.Flow

interface BuildNewsDataSource {
    fun getArticles(): Flow<Articles>
}
