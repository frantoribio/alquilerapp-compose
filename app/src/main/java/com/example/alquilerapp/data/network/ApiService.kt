package com.example.alquilerapp.data.network

import com.example.alquilerapp.data.model.Habitacion
import com.example.alquilerapp.data.model.LoginRequest
import com.example.alquilerapp.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("api/habitaciones")
    suspend fun getHabitaciones(): Response<List<Habitacion>>
    //suspend fun getHabitaciones(@Header("Authorization") auth: String? = null): Response<List<Habitacion>>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
