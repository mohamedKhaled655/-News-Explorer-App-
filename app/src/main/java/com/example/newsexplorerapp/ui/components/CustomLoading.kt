package com.example.newsexplorerapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CustomLoading() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CustomLottieUrl("https://lottie.host/ab9db099-d244-466c-bf9f-3275d5d898a6/Za7hULWJRA.lottie", size = 150)
    }


}