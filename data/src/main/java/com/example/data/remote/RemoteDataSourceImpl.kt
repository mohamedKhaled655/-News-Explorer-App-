package com.example.data.remote

import com.example.domain.model.NewsResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val service:NewsApiService) : RemoteDataSource {
    override suspend fun getNews(query: String, apikey: String): NewsResponse {
        return service.getNews(query,apikey)
    }

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}