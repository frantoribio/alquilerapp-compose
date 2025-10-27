package com.example.alquilerapp.data.network

import com.example.alquilerapp.network.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




object ApiServiceBuilder {
    fun create(): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { /* token JWT */ })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}