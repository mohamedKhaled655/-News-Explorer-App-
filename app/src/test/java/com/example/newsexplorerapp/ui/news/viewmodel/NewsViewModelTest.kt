package com.example.newsexplorerapp.ui.news.viewmodel

import com.example.domain.model.NewsResponse
import com.example.domain.usecase.GetLastQueryUseCase
import com.example.domain.usecase.GetNewsUseCase
import com.example.domain.usecase.SaveLastQueryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.example.core.utils.Result
import com.example.domain.model.Article
import com.example.domain.model.Source
import io.mockk.coVerify
import io.mockk.every
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle


@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {
    private lateinit var viewModel: NewsViewModel
    private val getNewsUseCase: GetNewsUseCase = mockk()
    private val saveLastQueryUseCase: SaveLastQueryUseCase = mockk(relaxed = true)
    private val getLastQueryUseCase: GetLastQueryUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val mockArticle = Article(
        title = "Test Article",
        description = "Test Description",
        url = "https://test.com",
        urlToImage = "https://test.com/image.jpg",
        publishedAt = "2025-01-01T00:00:00Z",
        source = Source( name = "Test Source"),
    )
    private val mockNewsResponse = NewsResponse(
        status = "ok",
        totalResults = 1,
        articles = listOf(mockArticle)
    )
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getLastQueryUseCase() } returns "android"
        coEvery { saveLastQueryUseCase(any()) } returns Unit
        every { getNewsUseCase(any()) } returns flowOf(Result.Success(mockNewsResponse))

        viewModel = NewsViewModel(getNewsUseCase, saveLastQueryUseCase, getLastQueryUseCase)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }




    @Test
    fun `searchNews emits Success after Loading for original method`() = runTest {
        val query = "kotlin"
        val mockResponse = NewsResponse("ok", 1, listOf(mockArticle))

        every { getNewsUseCase.invoke(query) } returns flowOf(Result.Success(mockResponse))

        // When
        viewModel.searchNews(query)

        advanceTimeBy(800)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.news.value is Result.Success)
        assertEquals(mockResponse, (viewModel.news.value as Result.Success).data)

        coVerify { saveLastQueryUseCase(query) }
    }



    @Test
    fun `loadLastQuery loads and searches with saved query`() = runTest {
        //Given
        val query = "saved-query"
        coEvery { getLastQueryUseCase() } returns query
        every { getNewsUseCase(query) } returns flowOf(Result.Success(mockNewsResponse))

        // When
        viewModel.loadLastQuery()

        advanceTimeBy(800)
        advanceUntilIdle()

        // Then
        coVerify { getLastQueryUseCase() }
        coVerify { getNewsUseCase(query) }
        assertTrue(viewModel.news.value is Result.Success)
    }


    @Test
    fun `loadLastQuery uses default when saved query is null`() = runTest {
        //Given
        coEvery { getLastQueryUseCase() } returns null
        every { getNewsUseCase("android") } returns flowOf(Result.Success(mockNewsResponse))

        // When
        viewModel.loadLastQuery()

        advanceTimeBy(800)
        advanceUntilIdle()

        // Then
        coVerify { getLastQueryUseCase() }
        coVerify { getNewsUseCase("android") }
        assertTrue(viewModel.news.value is Result.Success)
    }







}