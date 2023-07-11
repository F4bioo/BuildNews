package com.fappslab.buildnews.common.domain.repository

import com.fappslab.buildnews.common.domain.model.Articles
import kotlinx.coroutines.flow.Flow

interface BuildNewsRepository {
    fun getArticles(): Flow<Articles>
}
