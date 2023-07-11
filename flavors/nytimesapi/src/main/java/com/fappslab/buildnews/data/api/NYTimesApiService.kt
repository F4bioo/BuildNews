package com.fappslab.buildnews.data.api

import com.fappslab.buildnews.data.model.ArticlesResponse
import retrofit2.http.GET

interface NYTimesApiService {

    @GET("svc/mostpopular/v2/emailed/1.json")
    suspend fun getArticles(): ArticlesResponse
}
