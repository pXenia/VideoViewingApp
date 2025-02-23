package com.example.videoviewingapp.presentation.videolistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoviewingapp.domain.Video
import com.example.videoviewingapp.data.repository.VideoRepository
import com.example.videoviewingapp.data.sourse.VideoDao
import com.example.videoviewingapp.domain.VideoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val repository: VideoRepository,
    private val videoDao: VideoDao
) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        viewModelScope.launch {
            // проверяем есть ли кеш
            val cache = videoDao.getAllVideos()
            if (cache.isNotEmpty()) {
                _videos.value = cache.map {
                    Video(it.id, it.link, it.title, it.thumbnailUrl, it.duration)
                }
            } else {
                loadVideos()
            }
        }
    }

    fun loadVideos() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                _videos.value = repository.loadVideo()
                videoDao.clearVideos()
                videoDao.insertVideos(videos.value.map {
                    VideoEntity(it.id, it.link, it.title, it.thumbnailUrl, it.duration)
                })
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}