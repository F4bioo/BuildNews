package com.fappslab.buildnews.domain.repository

import com.fappslab.buildnews.domain.model.Articles
import kotlinx.coroutines.flow.Flow

interface BuildNewsRepository {
    fun getArticles(): Flow<Articles>
}
