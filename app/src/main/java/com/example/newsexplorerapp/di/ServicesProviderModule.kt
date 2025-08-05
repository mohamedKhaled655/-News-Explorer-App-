package com.example.newsexplorerapp.di


import com.example.data.remote.NewsApiService
import com.example.data.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class ServicesProviderModule {
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(RemoteDataSourceImpl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNewsService( retrofit: Retrofit) : NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}