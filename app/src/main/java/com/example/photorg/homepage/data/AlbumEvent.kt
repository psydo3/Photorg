package com.example.photorg.homepage.data

sealed interface AlbumEvent {
    object SaveAlbum: AlbumEvent
    data class SetName(val albumName: String): AlbumEvent
    data class SetColor(val albumColor: Int): AlbumEvent
    object ShowDialog: AlbumEvent
    object HideDialog: AlbumEvent
    data class SortAlbums(val sortType: SortType): AlbumEvent
    data class DeleteAlbum(val album: Album): AlbumEvent
}