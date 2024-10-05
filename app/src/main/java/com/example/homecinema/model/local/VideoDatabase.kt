package com.example.homecinema.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoEntity::class], version = 3, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}