package com.example.domain.usecase

import com.example.domain.model.NewsResponse
import com.example.domain.repo.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.core.utils.Result

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(query:String): Flow<Result<NewsResponse>> {
        return repository.getNews(query)
    }
}