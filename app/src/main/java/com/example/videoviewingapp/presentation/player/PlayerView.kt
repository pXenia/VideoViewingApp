package com.example.videoviewingapp.presentation.player

import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.videoviewingapp.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PlayerView(
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val player by playerViewModel.playerState.collectAsState()

    LaunchedEffect(Unit) {
        systemUiController.isSystemBarsVisible = false // cкрываем статус-бар и навигацию
    }

    DisposableEffect(Unit) {
        onDispose {
            playerViewModel.savePlayerState()
        }
    }
    Column {
        VideoView(player)
    }
}

@Composable
private fun VideoView(player: ExoPlayer?){

    var isControlsVisible by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isControlsVisible = !isControlsVisible
                        systemUiController.isSystemBarsVisible = isControlsVisible
                    }
                )
            },
        factory = {context ->
            PlayerView(context).apply {
                this.player = player
            }
        },
        update = {
            it.player = player
        }
    )
}
