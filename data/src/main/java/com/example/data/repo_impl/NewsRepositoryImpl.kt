package com.example.data.repo_impl

import com.example.core.utils.Constant
import com.example.data.local.LocalDataSource
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResponse
import com.example.domain.repo.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.core.utils.Result



class NewsRepositoryImpl @Inject constructor(private val remoteSource: RemoteDataSource, private val localDataSource: LocalDataSource):
    NewsRepository {

    private val apikey = Constant.NEWS_API_KEY
    override fun getNews(query: String): Flow<Result<NewsResponse>> = flow{
        emit(Result.Loading)
        try {
            val result = remoteSource.getNews(query, apikey)
            emit(Result.Success(result))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    override fun saveLastQuery(query: String) {
        localDataSource.saveLastQuery(query)
    }

    override fun getLastQuery(): String? {
        return localDataSource.getLastQuery()
    }


}