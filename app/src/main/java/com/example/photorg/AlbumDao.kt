package com.example.photorg

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Upsert
    suspend fun insertAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album): Int

    @Query("SELECT * FROM album ORDER BY albumName ASC")
    fun getAlbumsByName(): Flow<List<Album>>

    @Query("SELECT * FROM album ORDER BY albumName ASC")
    fun getAlbumsByColor(): Flow<List<Album>>
}