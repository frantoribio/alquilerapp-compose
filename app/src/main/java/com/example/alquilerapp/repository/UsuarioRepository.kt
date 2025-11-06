package com.example.alquilerapp.repository

import com.example.alquilerapp.data.model.Usuario
import com.example.alquilerapp.data.network.ApiService
import java.util.UUID


class UsuarioRepository(private val apiService: ApiService) {

    suspend fun obtenerUsuarios(): List<Usuario> {
        return apiService.listarUsuarios()
    }

    suspend fun crearUsuario(usuario: Usuario) {
        apiService.crearUsuario(usuario)
    }

    suspend fun actualizarUsuario(id: UUID, usuario: Usuario) {
        apiService.actualizarUsuario(id, usuario)
    }

    suspend fun eliminarUsuario(id: UUID) {
        apiService.eliminarUsuario(id)
    }
}
