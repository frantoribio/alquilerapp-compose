package com.example.alquilerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alquilerapp.data.TokenStore
import com.example.alquilerapp.repository.HabitacionesRepository
import com.example.alquilerapp.util.JwtUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = HabitacionesRepository()
    private val store = TokenStore(application.applicationContext)

    private val _role = MutableStateFlow<String?>(null)
    val role: StateFlow<String?> = _role

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

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
                        _role.value = role
                    }
                }
            } catch (e: Exception) {
                // ignore
            }
            _loading.value = false
        }
    }
}
