package com.example.videoviewingapp.presentation.videolistscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.videoviewingapp.R
import com.example.videoviewingapp.domain.Video
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
    val errorMessage by videoListViewModel.errorMessage.collectAsState()

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
            if (errorMessage != null) {
                // показываем ошибку
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = errorMessage!!,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { videoListViewModel.loadVideos() }) {
                        Text(text = stringResource(R.string.try_again))
                    }
                }
            } else {
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = { videoListViewModel.loadVideos() }
                ) {
                    VideoListScreenContent(navController, columnCount, cardHeight, videos)
                }
            }
        }
    }
}

@Composable
private fun VideoListScreenContent(
    navController: NavController,
    columnCount: Int,
    cardHeight: Dp,
    videos: List<Video>
){
    Column {
        Text(
            text = stringResource(R.string.popular_now),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            style = MaterialTheme.typography.titleLarge
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = videos, key = { it.id }) { video ->
                VideoCardItem(
                    modifier = Modifier.height(cardHeight),
                    video = Video(
                        id = video.id,
                        link = video.link,
                        title = video.title,
                        thumbnailUrl = video.thumbnailUrl,
                        duration = video.duration,
                    ),
                    onClick = { navController.navigate(Screen.PlayerScreen.route + "?link=${video.link}") }
                )
            }
        }
    }
}