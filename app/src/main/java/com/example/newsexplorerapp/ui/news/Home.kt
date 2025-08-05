package com.example.newsexplorerapp.ui.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsexplorerapp.ui.news.viewmodel.NewsViewModel

import com.example.newsexplorerapp.ui.components.CustomEmpty
import com.example.newsexplorerapp.ui.components.CustomError
import com.example.newsexplorerapp.ui.components.CustomLottieUrl
import com.example.newsexplorerapp.ui.news.views.NewsList
import com.example.newsexplorerapp.ui.news.views.SearchField
import com.example.core.utils.Result


@Composable
fun HomeScreen(viewModel: NewsViewModel = hiltViewModel(),navigateToNewsDetail: (url: String) -> Unit,) {
    val newsState = viewModel.news.collectAsState()
    var query by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.loadLastQuery()
    }

    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            SearchField(query, onClick = {
                query = ""

            }) {
                query = it
                viewModel.searchNews(it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (val state = newsState.value) {
                is Result.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomLottieUrl("https://lottie.host/ab9db099-d244-466c-bf9f-3275d5d898a6/Za7hULWJRA.lottie", size = 150)
                    }
                }
                is Result.Success -> {
                    if (state.data.articles.isNotEmpty()) {
                        NewsList(state.data.articles,navigateToNewsDetail)
                    } else {
                        CustomEmpty()
                    }
                }
                is Result.Failure -> {
                    CustomError()
                }

            }
        }
    }

}

