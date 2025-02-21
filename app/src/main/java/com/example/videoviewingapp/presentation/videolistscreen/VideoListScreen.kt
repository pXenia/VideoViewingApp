package com.example.videoviewingapp.presentation.videolistscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.videoviewingapp.R
import com.example.videoviewingapp.data.Video
import com.example.videoviewingapp.presentation.navigation.Screen

// определение размера экрана
@Composable
fun AdaptiveVideoListScreen(
    navController: NavController,
    windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            VideoListScreen(navController = navController, columnCount = 1)
        }

        WindowWidthSizeClass.Medium -> {
            VideoListScreen(navController = navController, columnCount = 2)
        }

        WindowWidthSizeClass.Expanded -> {
            VideoListScreen(navController = navController, columnCount = 3)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreen(
    navController: NavController,
    columnCount: Int,
    videoListViewModel: VideoListViewModel = hiltViewModel()
) {
    val videos by videoListViewModel.videos.collectAsState()
    val isRefreshing by videoListViewModel.isRefreshing.collectAsState()

    BoxWithConstraints {
        // количество VideoCardItem в столбце
        val cardHeight = when (columnCount) {
            1 -> maxHeight / 5.2f
            2 -> maxHeight / 3.2f
            else -> maxHeight / 2.2f
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { videoListViewModel.loadVideos() }
        ) {
            Column {
                Text(
                    text = stringResource(R.string.music_trends),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnCount),
                    modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(9) {
                        VideoCardItem(
                            modifier = Modifier.height(cardHeight),
                            video = Video(
                                id = "",
                                title = "Riley Green - Worst Way",
                                thumbnailUrl = "https://avatars.mds.yandex.net/i?id=85c711d5e3f31787f70136b9270d6179a73d8877-12423030-images-thumbs&n=13",
                                duration = "22:45",
                            ),
                            onClick = { navController.navigate(Screen.PlayerScreen.route) }
                        )
                    }
                }
            }
        }
    }
}