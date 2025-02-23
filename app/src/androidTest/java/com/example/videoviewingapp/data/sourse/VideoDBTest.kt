package com.example.videoviewingapp.data.sourse

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.videoviewingapp.domain.VideoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class VideoDaoTest {

    private lateinit var database: VideoDB
    private lateinit var videoDao: VideoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            VideoDB::class.java
        ).allowMainThreadQueries().build()

        videoDao = database.videoDao()
    }

    @Test
    fun insertAndGetVideos() = runTest {
        val videos = listOf(
            VideoEntity(1, "https://video1.mp4", "Title 1", "https://thumb1.jpg", 120),
            VideoEntity(2, "https://video2.mp4", "Title 2", "https://thumb2.jpg", 150)
        )

        videoDao.insertVideos(videos)
        val storedVideos = videoDao.getAllVideos()

        assertEquals(2, storedVideos.size)
        assertEquals("Title 1", storedVideos[0].title)
        assertEquals("https://video2.mp4", storedVideos[1].link)
    }

    @Test
    fun clearVideosTest() = runTest {
        val videos = listOf(
            VideoEntity(1, "https://video1.mp4", "Title 1", "https://thumb1.jpg", 120)
        )

        videoDao.insertVideos(videos)
        videoDao.clearVideos()

        val storedVideos = videoDao.getAllVideos()

        assertTrue(storedVideos.isEmpty())
    }


    @After
    fun teardown() {
        database.close()
    }
}
