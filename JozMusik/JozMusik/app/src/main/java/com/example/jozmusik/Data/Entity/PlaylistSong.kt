package com.example.jozmusik.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_songs")
data class PlaylistSong(
    @PrimaryKey
    val id: String,
    val songId: String,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val needSync: Boolean = true
)