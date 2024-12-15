package com.example.jozmusik.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jozmusik.Data.Entity.PlaylistSong
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist_songs")
    fun getPlaylistSongs(): Flow<List<PlaylistSong>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToPlaylist(song: PlaylistSong)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<PlaylistSong>)

    @Delete
    suspend fun removeFromPlaylist(song: PlaylistSong)

    // Tambahkan query untuk mendapatkan jumlah perubahan yang belum disync
    @Query("SELECT COUNT(*) FROM playlist_songs WHERE needSync = 1")
    suspend fun getUnsyncedCount(): Int

    @Query("SELECT * FROM playlist_songs WHERE needSync = 1")
    suspend fun getUnsyncedSongs(): List<PlaylistSong>
}