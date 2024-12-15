package com.example.jozmusik.network

import com.example.jozmusik.Model.Music
import com.example.jozmusik.Model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("user")
    fun getAllUsers(): Call<List<User>>

    @POST("user")
    fun createUser(@Body user: User): Call<User>

    @GET("music")
    fun getAllMusic(): Call<List<Music>>

    @POST("music")
    fun createMusic(@Body music : Music): Call<Music>

    @POST("music/{id}")
    fun updateMusic(@Path("id") Id: String, @Body music: Music): Call<Music>

    @DELETE("music/{id}")
    fun deleteMusic(@Path("id") Id: String): Call<Music>
}