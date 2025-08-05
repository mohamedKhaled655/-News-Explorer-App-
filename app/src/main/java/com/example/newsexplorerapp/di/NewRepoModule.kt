package com.example.newsexplorerapp.di


import com.example.data.local.LocalDataSource
import com.example.data.local.LocalDataSourceImpl
import com.example.data.remote.RemoteDataSource
import com.example.data.remote.RemoteDataSourceImpl
import com.example.data.repo_impl.NewsRepositoryImpl
import com.example.domain.repo.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class NewRepoModule{

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}