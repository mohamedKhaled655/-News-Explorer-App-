package com.example.newsexplorerapp.ui.news.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.domain.model.Article


@Composable
fun NewsList(articles: List<Article>, navigateToNewsDetail: (url: String) -> Unit) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        items(articles) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navigateToNewsDetail(article.url)
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(modifier = Modifier.padding(8.dp)) {

                    Column(modifier = Modifier.weight(1f)) {

                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(article.urlToImage)
                                .crossfade(true)
                                .build(),
                            contentDescription = article.urlToImage ?:"image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop

                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = article.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = article.description ?: "", maxLines = 2, style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Source: ${article.source.name}", style = MaterialTheme.typography.labelSmall)
                            Text(text = article.publishedAt, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                        }

                    }
                }
            }
        }
    }
}