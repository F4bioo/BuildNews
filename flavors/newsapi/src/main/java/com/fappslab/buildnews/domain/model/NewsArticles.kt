package com.fappslab.buildnews.domain.model

import com.fappslab.buildnews.common.domain.model.Articles

data class NewsArticles(
    val articles: List<NewsApiArticle>
) : Articles {

    data class NewsApiArticle(
        val title: String,
        val description: String,
        val source: String,
        val imageUrl: String,
        val articleUrl: String,
        val publishedAt: String,
    )
}
