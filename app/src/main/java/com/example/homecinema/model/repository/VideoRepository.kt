package com.example.homecinema.model.repository

import VideoListResponse
import com.example.homecinema.model.remote.VideoApi
import javax.inject.Inject


class VideoRepository @Inject constructor(private val videoApi: VideoApi) {
    suspend fun getVideos(): VideoListResponse {
        return videoApi.getVideos()
    }
}


