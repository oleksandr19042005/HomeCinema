package com.example.homecinema.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.homecinema.model.local.VideoDatabase
import com.example.homecinema.model.local.VideoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Volatile
    private var INSTANCE: VideoDatabase? = null

    @Provides
    @Singleton
    fun provideDatabase(context: Context): VideoDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                VideoDatabase::class.java,
                "videosCash"
            ).fallbackToDestructiveMigration().build()
            INSTANCE = instance
            instance
        }
    }

    @Provides
    @Singleton
    fun provideVideoDao(database: VideoDatabase): VideoDao {
        return database.videoDao()
    }
}