package com.example.jozmusik.UI.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jozmusik.Data.Dao.PlaylistDao
import com.example.jozmusik.Data.Entity.PlaylistSong
import com.example.jozmusik.Data.Entity.Song
import com.example.jozmusik.Data.Preferences.PreferenceManager
import com.example.jozmusik.Data.Repository.SongRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: SongRepository,
    private val prefManager: PreferenceManager,
    private val playlistDao: PlaylistDao
) : ViewModel() {

    private fun PlaylistSong.toSong(): Song {
        return Song(
            id = this.songId,  // gunakan songId bukan id
            title = this.title,
            artist = this.artist,
            imageUrl = this.imageUrl
        )
    }

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // Tambahkan ini untuk throttling
    private val addToPlaylistThrottle = MutableStateFlow(true)

    init {
        fetchSongs()
        // Bisa menambahkan ini untuk mencoba sync saat ViewModel diinisialisasi
        retryFailedUploads()
    }

    private fun fetchSongs() {
        viewModelScope.launch {
            try {
                repository.getAllSongs().collect {
                    _songs.value = it
                }
            } catch (e: Exception) {
                _error.value = "Error fetching songs: ${e.message}"
            }
        }
    }

    // Fungsi retry untuk songs yang gagal sync
    private fun retryFailedUploads() {
        viewModelScope.launch {
            val unsyncedSongs = playlistDao.getUnsyncedSongs()
            unsyncedSongs.forEach { playlistSong ->
                try {
                    // Konversi PlaylistSong ke Song sebelum menambahkan
                    addToPlaylist(playlistSong.toSong())
                } catch (e: Exception) {
                    Log.e("SongRepository", "Failed to sync song ${playlistSong.id}", e)
                }
            }
        }
    }

    fun addToPlaylist(song: Song) {
        viewModelScope.launch {
            if (addToPlaylistThrottle.value) {
                addToPlaylistThrottle.value = false
                _isLoading.value = true
                try {
                    println("Starting add to playlist process for song: ${song.title}")
                    repository.addToPlaylist(song)
                    println("Successfully completed add to playlist process")
                    _error.value = null
                } catch (e: Exception) {
                    println("Error in HomeViewModel: ${e.message}")
                    _error.value = e.message
                } finally {
                    _isLoading.value = false
                    delay(1000)
                    addToPlaylistThrottle.value = true
                }
            } else {
                println("Throttle active - waiting before adding another song")
                _error.value = "Please wait before adding another song"
            }
        }
    }
}