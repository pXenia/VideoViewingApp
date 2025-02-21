package com.example.videoviewingapp.presentation.player

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val exoPlayer: ExoPlayer,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState

    private var currentPosition: Long = savedStateHandle["current_position"] ?: 0L

    init {
        savedStateHandle.get<String>("url")?.let { url ->
            initializePlayer(url)
        } ?: initializePlayer("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
    }

    private fun initializePlayer(url: String) {
        if (_playerState.value == null) {
            viewModelScope.launch {
                exoPlayer.also {
                    val mediaItem = MediaItem.fromUri(Uri.parse(url))
                    it.setMediaItem(mediaItem)
                    it.prepare()
                    it.playWhenReady = true
                    it.seekTo(currentPosition)
                    it.addListener(object : Player.Listener {
                        override fun onPlayerError(error: PlaybackException) {
                        }
                    })
                }
                _playerState.value = exoPlayer
            }
        }
    }

    fun savePlayerState() {
        _playerState.value?.let {
            currentPosition = it.currentPosition
            savedStateHandle["current_position"] = currentPosition
        }
    }
}