package com.example.videoviewingapp.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.videoviewingapp.presentation.player.PlayerView
import com.example.videoviewingapp.presentation.videolistscreen.AdaptiveVideoListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    NavHost(
        navController = navController,
        startDestination = Screen.VideoListScreen.route
    ){
        composable(Screen.VideoListScreen.route) {
            AdaptiveVideoListScreen(
                navController = navController,
                windowSizeClass = windowSizeClass)
        }
        composable(Screen.PlayerScreen.route + "?link={link}",
            listOf(
                navArgument(
                    name = "link"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            PlayerView()
        }
    }
}