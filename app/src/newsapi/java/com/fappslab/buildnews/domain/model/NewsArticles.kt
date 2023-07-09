package com.fappslab.buildnews.domain.model

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
