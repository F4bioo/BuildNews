package com.fappslab.buildnews.data.api

import com.fappslab.buildnews.data.model.ApiResponse
import retrofit2.http.GET

interface BuildNewsService {

    @GET("v2/top-headlines?country=us")
    suspend fun getArticles(): ApiResponse
}
