package com.example.videoviewingapp.presentation.videolistscreen

import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoviewingapp.data.Video
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(

) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun loadVideos() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
            } catch (e: Exception) {

            } finally {
                _isRefreshing.value = false
            }
        }
    }
}