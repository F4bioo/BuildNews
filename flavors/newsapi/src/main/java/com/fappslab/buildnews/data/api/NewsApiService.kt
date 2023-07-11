package com.fappslab.buildnews.data.api

import com.fappslab.buildnews.data.model.ArticlesResponse
import retrofit2.http.GET

interface NewsApiService {

    @GET("v2/top-headlines?country=us")
    suspend fun getArticles(): ArticlesResponse
}
