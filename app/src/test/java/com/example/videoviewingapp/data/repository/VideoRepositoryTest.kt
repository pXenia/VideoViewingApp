package com.example.videoviewingapp.data.repository

import com.example.videoviewingapp.data.network.PexelsApiService
import com.example.videoviewingapp.domain.PexelsVideo
import com.example.videoviewingapp.domain.PexelsVideoListResponse
import com.example.videoviewingapp.domain.User
import com.example.videoviewingapp.domain.VideoFile
import com.example.videoviewingapp.domain.VideoPicture
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class VideoRepositoryTest {

    private lateinit var repository: VideoRepository
    private val apiService: PexelsApiService = mockk()

    @Before
    fun setUp() {
        repository = VideoRepository(apiService)
    }

    @Test
    fun `loadVideo returns video list on success`() = runTest {

        val mockResponse = PexelsVideoListResponse(
            videos = listOf(
                PexelsVideo(
                    id = 123,
                    url = "https://video-url.com",
                    video_files = listOf(VideoFile("https://video-url.mp4")),
                    user = User("Test User"),
                    video_pictures = listOf(VideoPicture("https://thumbnail-url.com")),
                    duration = 10
                )
            )
        )

        coEvery { apiService.loadVideos(any()) } returns mockResponse

        val (error, videos) = repository.loadVideo()

        assertNull(error)
        assertEquals(1, videos.size)
        assertEquals("https://video-url.mp4", videos[0].link)
        assertEquals("Test User", videos[0].title)
    }

    @Test
    fun `loadVideo returns empty list on network error`() = runTest {
        // Симуляция ошибки сети
        coEvery { apiService.loadVideos(any()) } throws IOException("Network error")

        val (error, videos) = repository.loadVideo()

        assertNotNull(error)
        assertTrue(error is IOException)
        assertEquals(0, videos.size)
    }

    @Test
    fun `loadVideo returns empty list on HttpException`() = runTest {
        // Симуляция ошибки 404
        coEvery { apiService.loadVideos(any()) } throws HttpException(
            Response.error<Any>(404, ResponseBody.create(null, ""))
        )

        val (error, videos) = repository.loadVideo()

        assertNotNull(error)
        assertTrue(error is HttpException)
        assertEquals(0, videos.size)
    }
}
