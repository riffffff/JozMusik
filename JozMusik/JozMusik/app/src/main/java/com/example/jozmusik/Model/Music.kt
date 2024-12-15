package com.example.jozmusik.Model


import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("judul_music")
    val judulMusic: String,
)
