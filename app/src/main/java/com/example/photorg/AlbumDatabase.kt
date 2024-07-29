package com.example.photorg

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Album::class],
    version = 3
)
abstract class AlbumDatabase : RoomDatabase() {
    abstract val dao: AlbumDao
}