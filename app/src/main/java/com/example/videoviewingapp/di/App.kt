package com.example.videoviewingapp.di

import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class App : Application()

@Module
@InstallIn(SingletonComponent::class)
object  AppModule {

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer{
        return ExoPlayer.Builder(context).build()
    }

//    @Provides
//    @Singleton
//    fun provideVideoRepository(): VideoRepository {
//        return VideoRepositoryImpl()
//    }
}

