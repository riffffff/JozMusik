package com.example.jozmusik.Data.Mapper

import com.example.jozmusik.Data.Api.Model.SongResponse
import com.example.jozmusik.Data.Entity.Song

fun SongResponse.toSong() = Song(
    id = id,
    title = title,
    artist = artist,
    imageUrl = imageUrl
)