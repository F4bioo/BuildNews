package com.fappslab.buildnews.stub

import com.fappslab.buildnews.domain.model.Articles
import com.fappslab.buildnews.domain.model.Articles.Article

fun articlesStub(): Articles =
    Articles(
        articles = listOf(
            Article(
                title = "Sample Article 1",
                description = "This is a sample article 1",
                source = "News Source 1",
                imageUrl = "image_url_1",
                articleUrl = "article_url_1",
                publishedAt = "2023-07-08"
            ),
            Article(
                title = "Sample Article 2",
                description = "This is a sample article 2",
                source = "News Source 2",
                imageUrl = "image_url_2",
                articleUrl = "article_url_2",
                publishedAt = "2023-07-09"
            )
        )
    )
