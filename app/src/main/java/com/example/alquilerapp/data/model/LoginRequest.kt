package com.example.alquilerapp.data.model

import com.google.gson.annotations.SerializedName

/** Representa la estructura de datos para una solicitud de inicio de sesión.
*
* Esta clase se utiliza para encapsular las credenciales (email y contraseña)
* que un usuario envía al servidor para autenticarse.
*
* @property email La dirección de correo electrónico del usuario.
* @property contraseña La contraseña del usuario para el inicio de sesión.
*/
data class LoginRequest(
    val email: String,
    @SerializedName("contraseña")
    val contraseña: String
)
