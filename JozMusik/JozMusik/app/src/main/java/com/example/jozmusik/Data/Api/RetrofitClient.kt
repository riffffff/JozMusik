package com.example.jozmusik.Data.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://ppbo-api.vercel.app/0BoBP/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request()
            println("----> ${request.method} ${request.url}")
            println("Headers: ${request.headers}")

            val requestBody = request.body
            if (requestBody != null) {
                println("Request Body: ${requestBody}")
            }

            val response = chain.proceed(request)
            println("<---- ${response.code} ${response.message}")
            println("Response Headers: ${response.headers}")

            if (response.body != null) {
                println("Response Body available")
            }

            response
        }
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}