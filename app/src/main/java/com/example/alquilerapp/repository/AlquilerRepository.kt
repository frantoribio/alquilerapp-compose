package com.example.alquilerapp.repository

import com.example.alquilerapp.data.model.Habitacion
import com.example.alquilerapp.data.model.UploadResponse
import com.example.alquilerapp.data.model.dto.CrearHabitacionDto
import com.example.alquilerapp.data.network.ApiService
import okhttp3.MultipartBody
import retrofit2.Response



class AlquilerRepository(private val apiService: ApiService) {
    suspend fun crearHabitacion(dto: CrearHabitacionDto): Habitacion {
        return apiService.crearHabitacion(dto)
    }

    suspend fun getHabitacionesPropietario(): List<Habitacion> {
        return apiService.getHabitacionesPropietario()
    }

    suspend fun uploadImage(image: MultipartBody.Part): Response<UploadResponse> {
        return apiService.uploadImage(image)
    }

}


