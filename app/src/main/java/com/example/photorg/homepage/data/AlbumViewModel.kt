package com.example.photorg.homepage.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlbumViewModel (
    private val dao: AlbumDao
): ViewModel(){
    private val _sortType = MutableStateFlow(SortType.COLOR)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _albums = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.NAME -> dao.getAlbumsByName()
                SortType.COLOR -> dao.getAlbumsByColor()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(AlbumsState())
    val state = combine(_state, _sortType, _albums){ state, sortType, albums ->
        state.copy(
            albums = albums,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AlbumsState())

    fun onEvent(event: AlbumEvent){
        when(event){
            is AlbumEvent.DeleteAlbum -> {
                viewModelScope.launch {
                    dao.deleteAlbum(event.album)
                }
            }
            AlbumEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingAlbum = false
                ) }
            }
            AlbumEvent.SaveAlbum -> {
                val albumName = state.value.albumName
                val albumColor = state.value.albumColor

                if(albumName.isBlank()){
                    return
                }

                val album = Album(
                    albumName = albumName,
                    albumColor = albumColor
                )
                viewModelScope.launch {
                    dao.insertAlbum(album)
                }
                _state.update { it.copy(
                    isAddingAlbum = false,
                    albumName = "",
                    albumColor = 0
                ) }
            }
            is AlbumEvent.SetColor -> {
                _state.update { it.copy(
                     albumColor = event.albumColor
                ) }
            }
            is AlbumEvent.SetName -> {
                _state.update { it.copy(
                    albumName = event.albumName
                ) }
            }
            AlbumEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingAlbum = true
                ) }
            }
            is AlbumEvent.SortAlbums -> {
                _sortType.value = event.sortType
            }
        }
    }
}