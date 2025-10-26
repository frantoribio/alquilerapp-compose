package com.example.alquilerapp.data.model

import java.util.UUID

/**
 * Clase que representa una reserva.
 *
 */
data class Reserva(
    val id: UUID? = null,
    val fechaInicio: String? = null,
    val fechaFin: String? = null,
    val habitacionId: UUID? = null
)