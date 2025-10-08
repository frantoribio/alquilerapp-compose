package com.example.alquilerapp.repository

import com.example.alquilerapp.data.model.LoginRequest
import com.example.alquilerapp.data.model.LoginResponse
import com.example.alquilerapp.data.model.Habitacion
import com.example.alquilerapp.data.network.RetrofitClient
import retrofit2.Response

class HabitacionesRepository {
    private val api = RetrofitClient.instance

    suspend fun getHabitaciones(): Response<List<Habitacion>> {

        return api.getHabitaciones()
    }

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }
}
