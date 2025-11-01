package com.example.alquilerapp.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO (Data Transfer Object) para enviar la información de una nueva habitación
 * al servidor.
 */
@Serializable
data class CrearHabitacionDto(
    val titulo: String,
    val ciudad: String,
    val direccion: String,
    @SerialName("precioMensual")
    val precioMensual: Double,
    val descripcion: String,
    val imagenesUrl: List<String>
)