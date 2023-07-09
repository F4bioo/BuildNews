package com.fappslab.buildnews.model

import com.fappslab.buildnews.common.domain.model.Articles

data class NYTimesArticles(
    val articles: List<NYTimesArticle>
) : Articles {

    data class NYTimesArticle(
        val title: String,
        val description: String,
        val source: String,
        val imageUrl: String,
        val articleUrl: String,
        val publishedAt: String,
    )
}