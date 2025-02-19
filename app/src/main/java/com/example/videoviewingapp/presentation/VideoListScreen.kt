package com.example.videoviewingapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.videoviewingapp.data.Video

// определение размера экрана
@Composable
fun AdaptiveVideoListScreen(modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            VideoListScreen(modifier, columnCount = 1)
        }

        WindowWidthSizeClass.Medium -> {
            VideoListScreen(modifier, columnCount = 2)
        }

        WindowWidthSizeClass.Expanded -> {
            VideoListScreen(modifier, columnCount = 3)
        }
    }
}

@Composable
fun VideoListScreen(modifier: Modifier, columnCount: Int) {
    BoxWithConstraints(modifier = modifier) {
        // количестов VideoCardItem в столбце
        val cardHeight = when (columnCount) {
            1 -> maxHeight / 5.2f
            2 -> maxHeight / 3.2f
            else -> maxHeight / 2.2f
        }
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
                        duration = "22:45"
                    ),
                )
            }
        }
    }
}

