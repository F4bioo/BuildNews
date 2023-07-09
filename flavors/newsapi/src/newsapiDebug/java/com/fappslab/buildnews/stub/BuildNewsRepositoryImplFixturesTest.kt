package com.fappslab.buildnews.stub

import androidx.annotation.VisibleForTesting
import com.fappslab.buildnews.common.domain.model.Articles
import com.fappslab.buildnews.data.model.ApiResponse
import com.fappslab.buildnews.libraries.arch.jsonhandle.readFromJSONToModel
import com.fappslab.buildnews.libraries.arch.jsonhandle.readFromJSONToString
import com.fappslab.buildnews.libraries.arch.rules.RemoteTestRule
import java.lang.reflect.Modifier
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

private const val NEWS_API_SUCCESS_RESPONSE = "news_api_success_response.json"
private const val NEWS_API_FAILURE_RESPONSE = "news_api_failure_response.json"

@VisibleForTesting(otherwise = Modifier.PRIVATE)
fun RemoteTestRule.toApiServerSuccessResponse() {
    val body = readFromJSONToString(NEWS_API_SUCCESS_RESPONSE)
    mockWebServerResponse(body, HttpURLConnection.HTTP_OK)
}

@VisibleForTesting(otherwise = Modifier.PRIVATE)
fun RemoteTestRule.toApiServerFailureResponse() {
    val body = readFromJSONToString(NEWS_API_FAILURE_RESPONSE)
    mockWebServerResponse(body, HttpsURLConnection.HTTP_BAD_REQUEST)
}

@VisibleForTesting(otherwise = Modifier.PRIVATE)
fun getArticlesStub(): Articles {
    return readFromJSONToModel<ApiResponse>(NEWS_API_SUCCESS_RESPONSE).toArticles()
}