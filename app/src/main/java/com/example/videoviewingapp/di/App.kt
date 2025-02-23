package com.example.videoviewingapp.di

import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.example.videoviewingapp.data.repository.VideoRepository
import com.example.videoviewingapp.data.network.PexelsApiService
import com.example.videoviewingapp.data.sourse.VideoDB
import com.example.videoviewingapp.data.sourse.VideoDao
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
object AppModule {

    // предоставляет экземпляр для воспроизведения видео
    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    // предоставляет экземпляр для парсинга
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // cоздает и предоставляет экземпляр retrofit для сетевых запросов
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // создает и предоставляет api сервис
    @Provides
    @Singleton
    fun providePexelsApiService(retrofit: Retrofit): PexelsApiService {
        return retrofit.create(PexelsApiService::class.java)
    }

    // предоставляет репозиторий для загрузки видео
    @Provides
    @Singleton
    fun provideVideoRepository(apiService: PexelsApiService): VideoRepository {
        return VideoRepository(apiService)
    }

    // cоздает и предоставляет бд
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VideoDB {
        return Room.databaseBuilder(
            context,
            VideoDB::class.java,
            "video_database"
        ).build()
    }

    // предоставляет dao
    @Provides
    fun provideVideoDao(database: VideoDB): VideoDao {
        return database.videoDao()
    }
}