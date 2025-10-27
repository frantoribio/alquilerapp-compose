package com.example.alquilerapp.di

import android.content.Context
import com.example.alquilerapp.data.TokenStore
import com.example.alquilerapp.data.network.ApiService
import com.example.alquilerapp.data.network.ApiServiceBuilder
import com.example.alquilerapp.network.AuthInterceptor
import com.example.alquilerapp.repository.UsuarioRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// Esta clase centraliza la creación de todas las dependencias
class AppContainer(private val applicationContext: Context) {



    // 1. Instancia del TokenStore (dependencia base)
    private val tokenStore: TokenStore = TokenStore(applicationContext)

    // 2. Definición de la función proveedora de tokens
    // Esta función bloquea el hilo (runBlocking) para obtener el token de DataStore
    // de manera síncrona, que es la forma en que OkHttp Interceptor lo requiere.
    private val tokenProvider: () -> String? = {
        // ADVERTENCIA: Usamos runBlocking porque OkHttp requiere que el interceptor
        // sea síncrono. Esto solo debe usarse en un interceptor.
        runBlocking {
            tokenStore.tokenFlow.first()
        }
    }

    // 3. Crear el OkHttpClient con el interceptor que usa el tokenProvider
    private val authInterceptor: AuthInterceptor = AuthInterceptor(tokenProvider)

    // 4. Instancia del ApiService (usa el ApiServiceBuilder)
    val apiService: ApiService = ApiServiceBuilder.create(authInterceptor)

    // 5. Instancia del Repositorio de Usuarios
    val usuarioRepository: UsuarioRepository = UsuarioRepository(apiService)
}
