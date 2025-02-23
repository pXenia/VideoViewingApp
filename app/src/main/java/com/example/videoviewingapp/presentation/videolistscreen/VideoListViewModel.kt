package com.example.videoviewingapp.presentation.videolistscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoviewingapp.domain.Video
import com.example.videoviewingapp.data.repository.VideoRepository
import com.example.videoviewingapp.data.sourse.VideoDao
import com.example.videoviewingapp.domain.VideoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
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

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

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
            clearCacheAndState()
            try {
                val result = repository.loadVideo()
                handleResult(result)
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    // очистка кеша и состояния
    private suspend fun clearCacheAndState() {
        _videos.value = emptyList()
        videoDao.clearVideos()
        _errorMessage.value = null
    }

    // обработка результата
    private suspend fun handleResult(result: Pair<Throwable?, List<Video>?>) {
        val (error, videoList) = result
        if (error != null) {
            handleError(error)
        } else if (videoList != null) {
            _videos.value = videoList
            cacheVideos(videoList)
        }
    }

    // обработка ошибок
    private fun handleError(error: Throwable) {
        _errorMessage.value = when (error) {
            is IOException -> "Ошибка сети. Проверьте подключение к интернету."
            is HttpException -> "Ошибка сервера. Попробуйте позже."
            else -> "Произошла ошибка. Повторите попытку."
        }
    }

    // кеширование видео
    private suspend fun cacheVideos(videos: List<Video>) {
        videoDao.insertVideos(videos.map {
            VideoEntity(it.id, it.link, it.title, it.thumbnailUrl, it.duration)
        })
    }
}