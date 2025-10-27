package com.example.alquilerapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilerapp.data.TokenStore
import com.example.alquilerapp.data.model.RegistroRequest
import com.example.alquilerapp.data.network.RetrofitClient
import com.example.alquilerapp.repository.HabitacionesRepository
import com.example.alquilerapp.util.JwtUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Clase de ViewModel para la autenticación.
 *
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = HabitacionesRepository()
    private val store = TokenStore(application.applicationContext)
    private val _role = MutableStateFlow<String?>(null)
    val role: StateFlow<String?> = _role
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val resp = repo.login(email, password)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    val token = body?.token
                    var role = body?.rol
                    // If backend returns token but not explicit role, try to decode role from JWT payload claim "role" or "roles"
                    if (!token.isNullOrEmpty() && role.isNullOrBlank()) {
                        val extracted = JwtUtils.extractClaim(token, "role") ?: JwtUtils.extractClaim(token, "roles")
                        if (!extracted.isNullOrBlank()) role = extracted
                    }
                    if (!token.isNullOrEmpty()) {
                        store.saveToken(token, role)
                        Log.d("LoginViewModel", "Token guardado: $token, rol: $role")
                        _role.value = role
                    }
                }
            } catch (e: Exception) {
                // ignore
            }
            _loading.value = false
        }
    }

    /**
     * Cierra la sesión actual.
     */
    fun logout() {
        _role.value = null
        // 2. Limpiar cualquier token o credencial persistente (SharedPreferences, DataStore, etc.)
        // Si usas tokens, añade aquí la lógica de limpieza.
        println("Sesión cerrada y estado reseteado.")
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param nombre El nombre del usuario.
     * @param email El correo electrónico del usuario.
     * @param contraseña La contraseña del usuario.
     * @param rol El rol del usuario (PROPIETARIO o ALUMNO").
     * @param onResult Una función de devolución de llamada que se llama con el resultado del registro.
     */
    fun registrar(nombre: String, email: String, contraseña: String, rol: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val request = RegistroRequest(nombre, email, contraseña, rol)
                val response = RetrofitClient.instance.registrarUsuario(request)
                if (response.isSuccessful) {
                    onResult(true, response.body()?.mensaje ?: "Registrado correctamente")
                } else {
                    onResult(false, "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                onResult(false, "Excepción: ${e.message}")
            }
        }
    }
}
