package com.example.jozmusik.Data.Api

import com.example.jozmusik.Data.Api.Model.PlaylistRequest
import com.example.jozmusik.Data.Entity.PlaylistSong
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("playlist")
    suspend fun addToPlaylist(@Body playlist: PlaylistRequest): PlaylistRequest

    @DELETE("playlist/{id}")
    suspend fun removeFromPlaylist(@Path("id") id: String): Response<Unit>

    @GET("playlist/{userId}")
    suspend fun getUserPlaylist(@Path("userId") userId: String): List<PlaylistSong>
}