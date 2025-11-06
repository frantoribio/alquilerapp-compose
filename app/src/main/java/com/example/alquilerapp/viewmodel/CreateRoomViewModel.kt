package com.example.alquilerapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilerapp.data.model.dto.CrearHabitacionDto
import com.example.alquilerapp.repository.AlquilerRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File

class CreateRoomViewModel(
    private val repository: AlquilerRepository
) : ViewModel() {

    // ==========================================================
    // 1. ESTADO DE LA UI (CAMPOS DE ENTRADA) - Faltaban estas referencias
    // ==========================================================
    var roomTitle by mutableStateOf("")
        private set
    var roomCity by mutableStateOf("")
        private set
    var roomAddress by mutableStateOf("")
        private set
    var roomPrice by mutableStateOf("") // Se mantiene como String para la entrada de texto
        private set
    var roomDescription by mutableStateOf("")
        private set
    var imageUrl by mutableStateOf("") // Campo para una URL de imagen
        private set

    // ==========================================================
    // 2. ESTADO DE LA OPERACIÓN Y ERRORES
    // ==========================================================
    var isSaving by mutableStateOf(false)
        private set
    var saveSuccess by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set


    // ==========================================================
    // 3. HANDLERS DE EVENTOS (Para actualizar el estado)
    // ==========================================================
    fun onTitleChange(newValue: String) { roomTitle = newValue }
    fun onCityChange(newValue: String) { roomCity = newValue }
    fun onAddressChange(newValue: String) { roomAddress = newValue }
    fun onDescriptionChange(newValue: String) { roomDescription = newValue }
    fun onImageUrlChange(newValue: String) { imageUrl = newValue }
    fun uploadImage(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
                inputStream?.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", tempFile.name, requestFile)

                val response = repository.uploadImage(body)
                if (response.isSuccessful) {
                    // Manejar éxito
                } else {
                    errorMessage = "Error al subir imagen: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Excepción: ${e.message}"
            }
        }
    }
    fun onImageSelected(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
                inputStream?.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", tempFile.name, requestFile)

                val response = repository.uploadImage(body)
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.url ?: ""
                    onImageUrlChange(imageUrl) // actualiza el campo en el formulario
                } else {
                    errorMessage = "Error al subir imagen: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Excepción al subir imagen: ${e.message}"
            }
        }
    }
    fun onPriceChange(newValue: String) {
        // Permite la entrada de números y el punto decimal
        if (newValue.all { it.isDigit() || it == '.' }) {
            roomPrice = newValue
        }
    }

    // ==========================================================
    // 4. LÓGICA DE CREACIÓN
    // ==========================================================
    fun createRoom() {
        // ... (Tu código existente)
        val priceValue = roomPrice.toDoubleOrNull()

        // --- Validación ---
        if (roomTitle.isBlank() || roomCity.isBlank() || roomAddress.isBlank() || priceValue == null) {
            errorMessage = "El título, la ciudad, la dirección y el precio son obligatorios."
            return
        }

        val imagesList = if (imageUrl.isNotBlank()) listOf(imageUrl) else emptyList()

        // 1. Crear el DTO
        val roomDto = CrearHabitacionDto(
            titulo = roomTitle,
            ciudad = roomCity,
            direccion = roomAddress,
            precioMensual = priceValue,
            descripcion = roomDescription,
            imagenesUrl = imagesList
        )

        // 2. Llamada a la API
        viewModelScope.launch {
            isSaving = true
            errorMessage = null
            try {
                // Llama al repositorio con el DTO simplificado
                repository.crearHabitacion(roomDto)
                saveSuccess = true
            } catch (e: Exception) {
                errorMessage = "Error al crear la habitación: ${e.message ?: "Error desconocido"}"
            } finally {
                isSaving = false
            }
        }
    }
}