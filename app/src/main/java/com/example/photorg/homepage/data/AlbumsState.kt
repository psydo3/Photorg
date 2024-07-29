package com.example.photorg.homepage.data

data class AlbumsState(
    val albums: List<Album> = emptyList(),
    val albumName: String = "",
    val albumColor: Int = 0,
    val isAddingAlbum: Boolean = false,
    val sortType: SortType = SortType.NAME
)