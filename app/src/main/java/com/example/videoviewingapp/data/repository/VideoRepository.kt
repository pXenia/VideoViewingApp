package com.example.videoviewingapp.data.repository

import com.example.videoviewingapp.BuildConfig
import com.example.videoviewingapp.domain.Video
import com.example.videoviewingapp.data.network.PexelsApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val pexelsApiService: PexelsApiService
) {
    suspend fun loadVideo(): Pair<Exception?, List<Video>> {
        try {
            val response = pexelsApiService.loadVideos(apiKey = BuildConfig.API_KEY_Pexels)
            return null to response.videos.map {
                Video(
                    id = it.id,
                    link = it.video_files[0].link,
                    title = it.user.name,
                    thumbnailUrl = it.video_pictures[0].picture,
                    duration = it.duration
                )
            }
        } catch (e: IOException) {
            return e to emptyList()
        } catch (e: HttpException) {
            return e to emptyList()
        } catch (e: Exception) {
            return e to emptyList()
        }
    }
}