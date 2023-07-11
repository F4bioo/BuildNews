package com.fappslab.buildnews.common.domain.model

data class Articles(
    val articles: List<Article>
) {

    data class Article(
        val title: String,
        val description: String,
        val source: String,
        val imageUrl: String,
        val articleUrl: String,
        val publishedAt: String
    )
}
