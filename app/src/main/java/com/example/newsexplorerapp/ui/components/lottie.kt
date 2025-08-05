package com.example.newsexplorerapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CustomLottieUrl(url:String,size:Int=50) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url(url))
    LottieAnimation(
        modifier = Modifier.size(size.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

}