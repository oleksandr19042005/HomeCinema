package com.example.homecinema.di

import android.app.Application
import android.content.Context
import com.example.homecinema.model.local.VideoDao
import com.example.homecinema.model.remote.VideoApi
import com.example.homecinema.model.repository.VideoDataBaseRepository
import com.example.homecinema.model.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    fun provideVideoRepository(videoApi: VideoApi): VideoRepository {
        return VideoRepository(videoApi)
    }

    @Provides
    fun provideVideoDatabaseRepository(dao: VideoDao): VideoDataBaseRepository {
        return VideoDataBaseRepository(dao)
    }
}
