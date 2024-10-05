package com.example.homecinema.model.repository

import com.example.homecinema.model.local.VideoDao
import com.example.homecinema.model.local.VideoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoDataBaseRepository @Inject constructor(private val dao: VideoDao) {

    suspend fun getAllVideos(): List<VideoEntity> {
        return withContext(Dispatchers.IO) {
            dao.getCashVideos()
        }
    }

    suspend fun insertCashVideos(videos: List<VideoEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertCashVideos(videos)
        }
    }


    suspend fun deleteCashVideos() {
        withContext(Dispatchers.IO) {
            dao.deleteCashVideos()
        }
    }
}

