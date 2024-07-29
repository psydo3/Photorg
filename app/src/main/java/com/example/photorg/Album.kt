package com.example.photorg

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Album(
    val albumName: String,
    val albumColor: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
