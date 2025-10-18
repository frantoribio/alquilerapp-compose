package com.example.alquilerapp.data.model

import java.util.UUID


data class Reserva(
    val id: UUID? = null,
    val fechaInicio: String? = null,
    val fechaFin: String? = null,
    val habitacionId: UUID? = null
    // puedes agregar más campos según tu API
)