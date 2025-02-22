package com.example.videoviewingapp.data.repository

import android.util.Log
import com.example.videoviewingapp.BuildConfig
import com.example.videoviewingapp.data.Video
import com.example.videoviewingapp.domain.PexelsApiService
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val pexelsApiService: PexelsApiService
) {
    suspend fun loadVideo(): List<Video> {
        val response = pexelsApiService.loadVideos(apiKey = BuildConfig.API_KEY_Pexels)
        return response.videos.map {
            Video(
                link = it.video_files[0].link,
                title = it.user.name,
                thumbnailUrl = it.video_pictures[0].picture,
                duration = it.duration
            )
        }
    }
}