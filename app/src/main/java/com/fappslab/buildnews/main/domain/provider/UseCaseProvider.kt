package com.fappslab.buildnews.main.domain.provider

import com.fappslab.buildnews.common.domain.usecase.GetArticlesUseCase
import com.fappslab.buildnews.common.domain.usecase.GetFormattedDateUseCase

data class UseCaseProvider(
    val getFormattedDateUseCase: GetFormattedDateUseCase,
    val getArticlesUseCase: GetArticlesUseCase
)
