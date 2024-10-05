package com.example.homecinema.model.remote

import VideoListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("videos?urls=true")
    suspend fun getVideos(
        @Query("api_key") apiKey: String = "e484dbe9c9d328e7dd18a6b1a202cb29"
    ): VideoListResponse
}



