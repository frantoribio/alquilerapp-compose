package com.example.alquilerapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilerapp.data.model.Usuario
import com.example.alquilerapp.repository.UsuarioRepository
import kotlinx.coroutines.launch
import java.util.UUID

class UsuariosViewModel(private val usuarioRepository: UsuarioRepository) : ViewModel() {

    var usuarios by mutableStateOf<List<Usuario>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun cargarUsuarios() {
        viewModelScope.launch {
            loading = true
            errorMessage = null
            try {
                usuarios = usuarioRepository.obtenerUsuarios()
            } catch (e: Exception) {
                errorMessage = "Error al cargar usuarios: ${e.message}"
            } finally {
                loading = false
            }
        }
    }

    fun eliminarUsuario(id: UUID) {
        viewModelScope.launch {
            try {
                usuarioRepository.eliminarUsuario(id)
                cargarUsuarios()
            } catch (e: Exception) {
                errorMessage = "Error al eliminar: ${e.message}"
            }
        }
    }

    fun crearUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                usuarioRepository.crearUsuario(usuario)
                cargarUsuarios()
            } catch (e: Exception) {
                errorMessage = "Error al crear: ${e.message}"
            }
        }
    }

    fun actualizarUsuario(id: String, usuario: Usuario) {
        viewModelScope.launch {
            try {
                usuarioRepository.actualizarUsuario(id, usuario)
                cargarUsuarios()
            } catch (e: Exception) {
                errorMessage = "Error al actualizar: ${e.message}"
            }
        }
    }
}