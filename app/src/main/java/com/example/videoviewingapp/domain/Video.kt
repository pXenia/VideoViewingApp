package com.example.videoviewingapp.domain

data class Video(
    val id: Int,
    val link: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: Int
)