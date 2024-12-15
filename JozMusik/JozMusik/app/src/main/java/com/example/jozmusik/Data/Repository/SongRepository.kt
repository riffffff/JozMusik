package com.example.jozmusik.Data.Repository

import com.example.jozmusik.Data.Api.ApiService
import com.example.jozmusik.Data.Api.Model.PlaylistRequest
import com.example.jozmusik.Data.Dao.PlaylistDao
import com.example.jozmusik.Data.Dao.SongDao
import com.example.jozmusik.Data.Entity.PlaylistSong
import com.example.jozmusik.Data.Entity.Song
import com.example.jozmusik.Data.Preferences.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val apiService: ApiService,
    private val songDao: SongDao,
    private val playlistDao: PlaylistDao,
    private val prefManager: PreferenceManager
) {
    fun getAllSongs(): Flow<List<Song>> = songDao.getAllSongs()

    suspend fun addToPlaylist(song: Song) {
        try {
            val username = prefManager.getUsername() ?: throw IllegalStateException("User not logged in")

            val request = PlaylistRequest(
                id = UUID.randomUUID().toString(), // Generate a unique ID
                songId = song.id,
                userId = username,
                title = song.title,
                artist = song.artist,
                imageUrl = song.imageUrl
            )

            println("Attempting to add song to playlist: ${song.title}")
            println("Request details: $request")

            val response = apiService.addToPlaylist(request)
            println("Successfully added to playlist. Response: $response")

            val playlistSong = PlaylistSong(
                id = response.id ?: UUID.randomUUID().toString(), // Fallback to a generated ID
                songId = song.id,
                title = song.title,
                artist = song.artist,
                imageUrl = song.imageUrl,
                needSync = false
            )
            playlistDao.insertToPlaylist(playlistSong)
            println("Song saved to local database: ${song.title}")

        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("500") == true -> {
                    println("Server error (500) while adding song: ${e.message}")
                    "Server error: Playlist limit reached"
                }
                e.message?.contains("404") == true -> {
                    println("Not found error (404) while adding song: ${e.message}")
                    "Server error: Resource not found"
                }
                else -> {
                    println("Unexpected error while adding song: ${e.message}")
                    "Failed to add song to playlist: ${e.message}"
                }
            }
            throw Exception(errorMessage)
        }
    }
}