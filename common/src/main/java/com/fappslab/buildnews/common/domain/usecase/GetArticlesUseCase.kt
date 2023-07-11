package com.fappslab.buildnews.common.domain.usecase

import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.common.domain.repository.BuildNewsRepository
import kotlinx.coroutines.flow.Flow

class GetArticlesUseCase(
    private val repository: BuildNewsRepository
) {

    operator fun invoke(): Flow<Articles> =
        repository.getArticles()
}
