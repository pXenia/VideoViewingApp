package com.example.videoviewingapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.videoviewingapp.data.Video

@Composable
fun VideoCardItem(
    modifier: Modifier = Modifier,
    video: Video
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // превью видео
            VideoCardItemImage(
                thumbnailUrl = video.thumbnailUrl,
                title = video.title
            )

            // название и продолжительность видео
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = video.title,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = video.duration,
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun VideoCardItemImage(
    thumbnailUrl: String,
    title: String
) {
    AsyncImage(
        model = thumbnailUrl,
        contentDescription = title,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.7f)
                    ),
                )
            )
            .clip(RoundedCornerShape(8.dp)),
    )
}
