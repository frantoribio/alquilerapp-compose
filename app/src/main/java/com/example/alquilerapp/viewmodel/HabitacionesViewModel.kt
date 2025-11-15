package com.example.alquilerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilerapp.data.model.Habitacion
import com.example.alquilerapp.repository.HabitacionesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Habitaciones
 */
class HabitacionesViewModel : ViewModel() {
    private val repo = HabitacionesRepository()

    private val _habitaciones = MutableStateFlow<List<Habitacion>>(emptyList())
    val habitaciones: StateFlow<List<Habitacion>> = _habitaciones

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun editarHabitacion(habitacion: Habitacion) {
        viewModelScope.launch {
            try {
                repo.editarHabitacion(habitacion.id, habitacion)
                loadHabitaciones()
            } catch (e: Exception) {
                _errorMessage.value = "Error al editar la habitación: ${e.message ?: "Desconocido"}"
            }
        }
    }
    fun loadHabitaciones(token: String? = null) {
        viewModelScope.launch {
            try {
                val resp = repo.getHabitaciones()
                if (resp.isSuccessful) {
                    _habitaciones.value = resp.body() ?: emptyList()
                } else {
                    _habitaciones.value = emptyList()
                }
            } catch (e: Exception) {
                _habitaciones.value = emptyList()
            }
        }
    }
}
