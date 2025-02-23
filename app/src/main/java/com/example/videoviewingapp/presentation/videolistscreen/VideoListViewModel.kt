package com.example.videoviewingapp.presentation.videolistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoviewingapp.domain.Video
import com.example.videoviewingapp.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        loadVideos()
    }

    fun loadVideos() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                _videos.value = repository.loadVideo()
            } catch (e: Exception) {

            } finally {
                _isRefreshing.value = false
            }
        }
    }
}