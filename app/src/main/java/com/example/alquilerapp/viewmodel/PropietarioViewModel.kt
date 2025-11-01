package com.example.alquilerapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilerapp.data.model.Habitacion // Asegúrate de tener la importación correcta
import com.example.alquilerapp.repository.AlquilerRepository
import kotlinx.coroutines.launch

class PropietarioViewModel(
    private val repository: AlquilerRepository
) : ViewModel() {

    // Estado que contiene la lista de habitaciones
    var habitaciones by mutableStateOf<List<Habitacion>>(emptyList())
        private set

    // Estado para saber si los datos están cargando
    var isLoading by mutableStateOf(false)
        private set

    // Estado para manejar errores
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        cargarHabitacionesPropietario()
    }

    /**
     * Carga las habitaciones asociadas al propietario autenticado.
     * Se asume que el backend filtra por el usuario autenticado.
     */
    fun cargarHabitacionesPropietario() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                // Suponemos que tienes un endpoint en tu API:
                // GET /habitaciones/propietario
                habitaciones = repository.getHabitacionesPropietario()
            } catch (e: Exception) {
                errorMessage = "Error al cargar las habitaciones: ${e.message ?: "Desconocido"}"
            } finally {
                isLoading = false
            }
        }
    }
}