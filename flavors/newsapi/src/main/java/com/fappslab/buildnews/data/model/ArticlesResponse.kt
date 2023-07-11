package com.fappslab.buildnews.data.model

import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.common.domain.model.Articles.Article
import com.fappslab.buildnews.libraries.arch.extension.orDash
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val articles: List<ArticleResponse?>?
) {

    data class ArticleResponse(
        @SerializedName("source")
        val source: SourceResponse?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("urlToImage")
        val urlToImage: String?,
        @SerializedName("publishedAt")
        val publishedAt: String?
    ) {

        data class SourceResponse(
            @SerializedName("name")
            val name: String?
        )
    }

    fun toArticles(): Articles {
        return Articles(
            articles?.map { it.toArticle() }.orEmpty()
        )
    }

    private fun ArticleResponse?.toArticle(): Article {
        return Article(
            title = this?.title.orDash(),
            description = this?.description.orDash(),
            source = this?.source?.name.orDash(),
            imageUrl = this?.urlToImage.orEmpty(),
            articleUrl = this?.url.orEmpty(),
            publishedAt = this?.publishedAt.orDash()
        )
    }
}
