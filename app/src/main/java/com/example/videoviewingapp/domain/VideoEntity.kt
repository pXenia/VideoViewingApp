package com.example.videoviewingapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val id: Int,
    val link: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: Int
)