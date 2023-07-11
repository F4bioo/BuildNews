package com.fappslab.buildnews.data.model

import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.common.domain.model.Articles.Article
import com.fappslab.buildnews.libraries.arch.extension.orDash
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("results")
    val articles: List<ArticleResponse?>?
) {

    data class ArticleResponse(
        @SerializedName("url")
        val url: String?,
        @SerializedName("source")
        val source: String?,
        @SerializedName("published_date")
        val publishedDate: String?,
        @SerializedName("byline")
        val byline: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("abstract")
        val abstract: String?,
        @SerializedName("media")
        val media: List<MediaResponse?>?
    ) {

        data class MediaResponse(
            @SerializedName("media-metadata")
            val mediaMetadata: List<MediaMetadataResponse?>?
        ) {

            data class MediaMetadataResponse(
                @SerializedName("url")
                val url: String
            )
        }
    }

    fun toArticles(): Articles {
        return Articles(
            articles = articles?.map { it.toArticle() }.orEmpty()
        )
    }

    private fun ArticleResponse?.toArticle(): Article {
        return Article(
            title = this?.title.orDash(),
            description = this?.abstract.orDash(),
            source = this?.source.orDash(),
            imageUrl = this?.media?.firstOrNull()?.mediaMetadata?.lastOrNull()?.url.orEmpty(),
            articleUrl = this?.url.orEmpty(),
            publishedAt = this?.publishedDate.orDash()
        )
    }
}
