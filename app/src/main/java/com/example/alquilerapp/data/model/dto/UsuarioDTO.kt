package com.example.alquilerapp.data.model.dto

data class UsuarioDTO(
    val id: String? = null,
    val nombre: String,
    val email: String,
    val contraseña: String? = null,
    val rol: String
)