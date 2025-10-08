package com.example.alquilerapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Habitacion(
    val id: String,
    val titulo: String,
    val ciudad: String,
    val direccion: String,
    @SerialName("precioMensual") // <--- ¡Asegúrate de tener esta anotación!
    val precioMensual: Double,
    val descripcion: String,
    val imagenesUrl: List<String>,
    // ... otros campos
)
    fun Habitacion.getEmulatedImageUrl(): String? {
        // Tomamos la primera imagen y corregimos la URL para el emulador
        return this.imagenesUrl.firstOrNull()?.replace("localhost", "10.0.2.2")
    }
