package com.example.newsexplorerapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute(
    val title: String,
) {
    // Home
    @Serializable
    object HomeScreenRoute : ScreenRoute("HomeScreen")

    //Checkout Screen
    @Serializable
    data class DetailScreenRoute(val url:String) : ScreenRoute("DetailScreenRoute")



}