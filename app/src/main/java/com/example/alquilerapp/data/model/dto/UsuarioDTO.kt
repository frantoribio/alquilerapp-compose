package com.example.alquilerapp.data.model.dto

import java.util.UUID

data class UsuarioDTO(
    val id: UUID? = null,
    val nombre: String,
    val email: String,
    val contraseña: String? = null,
    val rol: String
)