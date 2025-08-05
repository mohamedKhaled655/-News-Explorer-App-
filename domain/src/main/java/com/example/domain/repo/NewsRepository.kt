package com.example.domain.repo

import com.example.domain.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import com.example.core.utils.Result

interface NewsRepository {
    fun getNews(query: String) : Flow<Result<NewsResponse>>

    fun saveLastQuery(query: String)
    fun getLastQuery(): String?
}