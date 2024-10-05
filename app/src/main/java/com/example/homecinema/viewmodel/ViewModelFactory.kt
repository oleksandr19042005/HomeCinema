package com.example.homecinema.viewmodel

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homecinema.model.repository.VideoDataBaseRepository
import com.example.homecinema.model.repository.VideoRepository
import ui.video_list.VideoListViewModel

class ViewModelFactory(
    private val videoDataBaseRepository: VideoDataBaseRepository,
    private val videoRepository: VideoRepository,
    private val connectivityManager: ConnectivityManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VideoListViewModel(videoDataBaseRepository, videoRepository,connectivityManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
