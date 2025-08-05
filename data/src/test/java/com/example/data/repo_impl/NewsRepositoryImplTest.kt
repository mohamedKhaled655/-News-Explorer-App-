package com.example.data.repo_impl

import com.example.core.utils.Result
import com.example.data.local.LocalDataSource
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.Article
import com.example.domain.model.NewsResponse
import com.example.domain.model.Source
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryImplTest {
    private lateinit var repositoryImpl: NewsRepositoryImpl
    private val remoteDataSource:RemoteDataSource = mockk()
    private val localDataSource:LocalDataSource = mockk()

    @Before
    fun setup(){
        repositoryImpl = NewsRepositoryImpl(remoteDataSource,localDataSource)

    }


    @Test
    fun getNews_returnList_when_succeeds() = runTest {
        //Given
        val query = "android"
        val mockResponse = NewsResponse(status = "ok", totalResults = 1, articles = listOf(
            Article(
                title = "Test Title",
                description = "Test Description",
                url = "https://example.com",
                urlToImage = null,
                publishedAt = "2025-08-04",
                source = Source("")
            )
        ))
        coEvery { remoteDataSource.getNews(query,any()) } returns mockResponse

        //When
        val result = repositoryImpl.getNews(query).toList()

        //Then
        assertEquals(Result.Loading, result[0])
        assertEquals(Result.Success(mockResponse), result[1])


    }

    @Test
    fun getNews_returnError_when_exceptionThrown() = runTest {
        // Given
        val query = "android"
        val exceptionMessage = "Network Error"
        coEvery { remoteDataSource.getNews(query, any()) } throws Exception(exceptionMessage)

        // When
        val result = repositoryImpl.getNews(query).toList()

        // Then
        assertEquals(Result.Loading, result[0])
        assertTrue(result[1] is Result.Failure)

    }

    @Test
    fun saveLastQuery_callsLocalDataSource() = runTest {
        // Given
        val query = "android"
        coEvery { localDataSource.saveLastQuery(query) } returns Unit

        // When
        repositoryImpl.saveLastQuery(query)

        // Then
        coVerify(exactly = 1) { localDataSource.saveLastQuery(query) }
    }

    @Test
    fun getLastQuery_returnValueFromLocalDataSource() = runTest {
        // Given
        val expectedQuery = "android"
        coEvery { repositoryImpl.getLastQuery() } returns expectedQuery

        // When
        val result = repositoryImpl.getLastQuery()

        // Then
        assertEquals(expectedQuery, result)
    }



}