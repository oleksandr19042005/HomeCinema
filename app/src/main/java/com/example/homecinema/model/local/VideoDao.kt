package com.example.homecinema.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCashVideos(videos: List<VideoEntity>)

    @Query("SELECT * FROM videosCash")
    fun getCashVideos():List<VideoEntity>

    @Query("DELETE FROM videosCash")
    fun deleteCashVideos()
}
