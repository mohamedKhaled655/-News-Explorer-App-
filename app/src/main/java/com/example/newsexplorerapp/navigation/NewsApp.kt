package com.example.newsexplorerapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsexplorerapp.ui.news.HomeScreen
import com.example.newsexplorerapp.ui.news_detail.DetailScreen


@Composable
fun NewsApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeScreenRoute
    ){
        composable<ScreenRoute.HomeScreenRoute> {
            HomeScreen(
                navigateToNewsDetail = { url ->
                    navController.navigate(ScreenRoute.DetailScreenRoute(url = url))
                }
            )
        }

        composable<ScreenRoute.DetailScreenRoute> { navBackStackEntry ->
            val url: String = navBackStackEntry.toRoute<ScreenRoute.DetailScreenRoute>().url
            DetailScreen(url)
        }
    }

}