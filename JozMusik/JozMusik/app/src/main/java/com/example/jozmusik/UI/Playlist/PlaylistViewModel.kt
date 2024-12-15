package com.example.jozmusik.UI.Playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jozmusik.Data.Entity.PlaylistSong
import com.example.jozmusik.Data.Repository.PlaylistRepository
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {

    private val _playlistSongs = MutableLiveData<List<PlaylistSong>>()
    val playlistSongs: LiveData<List<PlaylistSong>> = _playlistSongs

    init {
        getPlaylist()
    }

    private fun getPlaylist() {
        viewModelScope.launch {
            try {
                repository.getPlaylistSongs().collect {
                    _playlistSongs.value = it
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun removeFromPlaylist(song: PlaylistSong) {
        viewModelScope.launch {
            try {
                println("Starting remove from playlist process for song: ${song.title}")
                repository.removeFromPlaylist(song)
                println("Successfully completed remove from playlist process")
            } catch (e: Exception) {
                println("Error in PlaylistViewModel: ${e.message}")
                // Handle error appropriately
            }
        }
    }
}