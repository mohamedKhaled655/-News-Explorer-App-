package com.example.data.remote

import com.example.domain.model.NewsResponse

interface RemoteDataSource {
    suspend fun getNews(query: String,apikey:String) : NewsResponse
}