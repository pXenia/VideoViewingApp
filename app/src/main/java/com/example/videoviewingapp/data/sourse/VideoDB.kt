package com.example.videoviewingapp.data.sourse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.videoviewingapp.domain.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class VideoDB : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}