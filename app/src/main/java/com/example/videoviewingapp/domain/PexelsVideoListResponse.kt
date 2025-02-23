package com.example.videoviewingapp.domain

data class PexelsVideoListResponse(
    val videos: List<PexelsVideo>
)

data class PexelsVideo(
    val id: Int,
    val url: String,
    val duration: Int,
    val user: User,
    val video_files: List<VideoFile>,
    val video_pictures: List<VideoPicture>
)

data class User(
    val name: String,
)

data class VideoFile(
    val link: String
)

data class VideoPicture(
    val picture: String,
)
