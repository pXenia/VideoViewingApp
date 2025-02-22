package com.example.videoviewingapp.domain

import com.example.videoviewingapp.data.PexelsVideoListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApiService {
    @GET("videos/popular")
    suspend fun loadVideos(
        @Header("Authorization") apiKey: String,
        @Query("per_page") perPage: Int = 10
    ): PexelsVideoListResponse
}