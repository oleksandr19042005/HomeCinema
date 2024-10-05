package com.example.homecinema.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "videosCash")
data class VideoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("duration")
    val duration: Double,
)

