package com.example.photorg.homepage.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Album::class],
    version = 5
)
abstract class AlbumDatabase : RoomDatabase() {
    abstract val dao: AlbumDao
}