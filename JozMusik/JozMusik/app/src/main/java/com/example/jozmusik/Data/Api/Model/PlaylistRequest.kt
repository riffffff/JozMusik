package com.example.jozmusik.Data.Api.Model


data class PlaylistRequest(
    val id: String,
    val songId: String,
    val userId: String,
    val title: String,
    val artist: String,
    val imageUrl: String
)