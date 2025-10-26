package com.example.alquilerapp.data.model

/**
 * Clase que representa la respuesta de un registro.
 */
data class RegistroResponse(
    val email: String,
    val token: String,
    val rol: String,
    val mensaje: String?
)
