package com.example.domain.usecase

import com.example.domain.repo.NewsRepository
import javax.inject.Inject

class GetLastQueryUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke () : String?{
        return repository.getLastQuery()
    }
}