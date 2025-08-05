package com.example.data.remote

import com.example.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("everything")
    suspend fun getNews (
        @Query("q") query:String = "android",
        @Query("apiKey") apikey:String = "8d93596ade814720822aee95b3071743"
    ) : NewsResponse
}