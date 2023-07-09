package com.fappslab.buildnews.data.api

import com.fappslab.buildnews.data.model.ApiResponse
import retrofit2.http.GET

interface BuildNewsService {

    @GET("svc/mostpopular/v2/emailed/1.json")
    suspend fun getArticles(): ApiResponse
}
