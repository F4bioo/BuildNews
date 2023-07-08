package com.fappslab.buildnews.domain.usecase

import com.fappslab.buildnews.domain.model.Articles
import com.fappslab.buildnews.domain.repository.BuildNewsRepository
import kotlinx.coroutines.flow.Flow

class GetArticlesUseCase(
    private val repository: BuildNewsRepository
) {

    operator fun invoke(): Flow<Articles> =
        repository.getArticles()
}
