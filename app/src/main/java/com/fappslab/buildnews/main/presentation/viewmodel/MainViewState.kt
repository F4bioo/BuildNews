package com.fappslab.buildnews.main.presentation.viewmodel

import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.common.domain.model.Articles.Article

const val LOADING_CHILD = 0
const val BIOMETRIC_CHILD = 1
const val FAILURE_CHILD = 2
const val SUCCESS_CHILD = 3

data class MainViewState(
    val childPosition: Int = LOADING_CHILD,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val shouldShowDetails: Boolean = false,
    val shouldShowPrompt: Boolean = false,
    val articles: List<Article>? = null,
    val errorMessage: String? = null,
    val article: Article? = null
) {

    fun getArticlesFailure(message: String?) = copy(
        shouldShowError = true,
        errorMessage = message,
        childPosition = FAILURE_CHILD
    )

    fun getArticlesSuccess(articles: Articles) = copy(
        articles = articles.articles,
        childPosition = SUCCESS_CHILD
    )

    fun showDetailsState(article: Article, formattedDate: String) = copy(
        article = article.copy(publishedAt = formattedDate),
        shouldShowDetails = true
    )
}
