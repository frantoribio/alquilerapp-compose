package com.example.alquilerapp.data.network

import com.example.alquilerapp.data.model.Habitacion
import com.example.alquilerapp.data.model.LoginRequest
import com.example.alquilerapp.data.model.LoginResponse
import com.example.alquilerapp.data.model.RegistroRequest
import com.example.alquilerapp.data.model.RegistroResponse
import com.example.alquilerapp.data.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.UUID

/**
 * Interfaz que define los puntos finales de la API para el alquiler de habitaciones.
 *
 */
interface ApiService {

    /**
     * Obtiene la lista de habitaciones disponibles para alquilar.
     *
     * @return Una respuesta que contiene la lista de habitaciones.
     */
    @GET("api/habitaciones")
    suspend fun getHabitaciones(): Response<List<Habitacion>>
    //suspend fun getHabitaciones(@Header("Authorization") auth: String? = null): Response<List<Habitacion>>

    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param request El objeto que contiene las credenciales de inicio de sesión.
     */
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    /**
     * Registra un nuevo usuario.
     *
     * @param request El objeto que contiene los datos del nuevo usuario.
     */
    @POST("api/usuarios/registro")
    suspend fun registrarUsuario(@Body request: RegistroRequest): Response<RegistroResponse>

    @GET("usuarios")
    suspend fun listarUsuarios(): List<Usuario>

    @PUT("api/usuarios/{id}")
    suspend fun crearUsuario(usuario: Usuario): Usuario

    @POST("api/usuarios/{id}")
    suspend fun actualizarUsuario(id: String, usuario: Usuario): Usuario

    @POST("api/usuarios/{id}")
    suspend fun eliminarUsuario(id: UUID): Usuario

}
