package com.example.alquilerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alquilerapp.repository.AlquilerRepository

/**
 * Fábrica para crear una instancia de CreateRoomViewModel
 * ya que requiere una dependencia (AlquilerRepository).
 */
class CreateRoomViewModelFactory(
    private val repository: AlquilerRepository
) : ViewModelProvider.Factory {

    // Suprime la advertencia de casting no verificado
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateRoomViewModel::class.java)) {
            return CreateRoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}