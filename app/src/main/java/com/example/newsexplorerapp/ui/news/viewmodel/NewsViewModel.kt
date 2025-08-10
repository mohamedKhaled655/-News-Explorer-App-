package com.example.newsexplorerapp.ui.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.core.utils.Result
import com.example.domain.model.NewsResponse
import com.example.domain.usecase.GetLastQueryUseCase
import com.example.domain.usecase.SaveLastQueryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: GetNewsUseCase,
    private val saveLastQueryUseCase: SaveLastQueryUseCase,
    private val getNewsUseCase: GetLastQueryUseCase
) : ViewModel() {
    private val _news = MutableStateFlow<Result<NewsResponse>>(Result.Loading)
    val news  = _news.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery.debounce(700)
                .distinctUntilChanged()
                .collect{ query ->
                    if (query.isNotBlank()){
                        getNews(query)
                    }

                }
        }
    }

    fun onSearchQueryChanged(query: String){
        _searchQuery.value = query
    }

    private suspend fun getNews(query: String){
        newsUseCase.invoke(query)
            .flowOn(Dispatchers.IO)
            .catch { e -> _news.value = Result.Failure(e) }
            .collect{result ->
                _news.value = result
                saveLastQueryUseCase(query)
            }
    }


    fun searchNews(query: String){
        viewModelScope.launch {
            newsUseCase.invoke(query)
                .flowOn(Dispatchers.IO)
                .debounce(700)
                .distinctUntilChanged()
                .catch { e -> _news.value = Result.Failure(e) }
                .collect{ result ->
                    _news.value = result
                    saveLastQueryUseCase(query)
                }

        }
    }

    fun loadLastQuery() {
        viewModelScope.launch {
            val lastQuery = getNewsUseCase() ?: "android"
            searchNews(lastQuery)
        }
    }




}