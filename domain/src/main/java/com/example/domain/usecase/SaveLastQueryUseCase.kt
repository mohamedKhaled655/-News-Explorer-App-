package com.example.domain.usecase

import com.example.domain.repo.NewsRepository
import javax.inject.Inject

class SaveLastQueryUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke (query: String){
        return repository.saveLastQuery(query)
    }
}