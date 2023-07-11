package com.fappslab.buildnews.stub

import androidx.annotation.VisibleForTesting
import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.data.model.ArticlesResponse
import com.fappslab.buildnews.libraries.arch.jsonhandle.readFromJSONToModel
import com.fappslab.buildnews.libraries.arch.jsonhandle.readFromJSONToString
import com.fappslab.buildnews.libraries.arch.rules.RemoteRule
import java.lang.reflect.Modifier
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

private const val NEWS_API_SUCCESS_RESPONSE = "news_api_success_response.json"
private const val NEWS_API_FAILURE_RESPONSE = "news_api_failure_response.json"

@VisibleForTesting(otherwise = Modifier.PRIVATE)
fun RemoteRule.toApiServerSuccessResponse() {
    val body = readFromJSONToString(NEWS_API_SUCCESS_RESPONSE)
    mockWebServerResponse(body, HttpURLConnection.HTTP_OK)
}

@VisibleForTesting(otherwise = Modifier.PRIVATE)
fun RemoteRule.toApiServerFailureResponse() {
    val body = readFromJSONToString(NEWS_API_FAILURE_RESPONSE)
    mockWebServerResponse(body, HttpsURLConnection.HTTP_BAD_REQUEST)
}

@VisibleForTesting(otherwise = Modifier.PRIVATE)
fun getArticlesStub(): Articles {
    return readFromJSONToModel<ArticlesResponse>(NEWS_API_SUCCESS_RESPONSE)
        .toArticles()
}

@VisibleForTesting(otherwise = Modifier.PRIVATE)
fun getArticleStub(): Articles.Article {
    return readFromJSONToModel<ArticlesResponse>(NEWS_API_SUCCESS_RESPONSE)
        .toArticles()
        .articles.first()
}
