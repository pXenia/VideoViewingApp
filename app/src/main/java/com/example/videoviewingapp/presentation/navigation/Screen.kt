package com.example.videoviewingapp.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object PlayerScreen: Screen("player")
    object VideoListScreen: Screen("videos")
}