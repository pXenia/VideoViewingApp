package com.example.videoviewingapp.di

import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.videoviewingapp.data.repository.VideoRepository
import com.example.videoviewingapp.domain.PexelsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    // для конвертации json
    @Provides
    @Singleton
    fun providedMoshi(): Moshi {
        return  Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // для сетевых запросов
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // создает api сервис
    @Provides
    @Singleton
    fun providePixelsApiService(retrofit: Retrofit): PexelsApiService {
        return retrofit.create(PexelsApiService::class.java)
    }

    // управляет получением видео
    @Provides
    @Singleton
    fun provideVideoRepository(apiService: PexelsApiService): VideoRepository {
        return VideoRepository(apiService)
    }
}

