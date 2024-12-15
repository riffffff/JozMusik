package com.example.jozmusik.Data.Repository

import com.example.jozmusik.Data.Api.ApiService
import com.example.jozmusik.Data.Dao.PlaylistDao
import com.example.jozmusik.Data.Entity.PlaylistSong
import com.example.jozmusik.Data.Preferences.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val apiService: ApiService,
    private val playlistDao: PlaylistDao,
    private val prefManager: PreferenceManager
) {
    fun getPlaylistSongs(): Flow<List<PlaylistSong>> = flow {
        emitAll(playlistDao.getPlaylistSongs())

        try {
            val username = prefManager.getUsername() ?: return@flow
            val playlistSongs = apiService.getUserPlaylist(username)
            playlistDao.insertSongs(playlistSongs)
        } catch (e: Exception) {
            println("Error fetching playlist: ${e.message}")
        }
    }

    suspend fun removeFromPlaylist(song: PlaylistSong) {
        try {
            println("Attempting to remove song from playlist: ${song.title}")
            println("Delete request for song ID: ${song.id}")

            // Coba hapus dari API
            apiService.removeFromPlaylist(song.id)
            println("Successfully removed from playlist API")

            // Hapus dari database lokal
            playlistDao.removeFromPlaylist(song)
            println("Successfully removed from local database")

        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("500") == true -> {
                    println("Server error (500) while removing song: ${e.message}")
                    "Server error while removing song"
                }
                e.message?.contains("404") == true -> {
                    println("Not found error (404) while removing song: ${e.message}")
                    "Song not found on server"
                }
                else -> {
                    println("Unexpected error while removing song: ${e.message}")
                    "Failed to remove song: ${e.message}"
                }
            }
            println("Error details: ${e.stackTraceToString()}")
            throw Exception(errorMessage)
        }
    }
}