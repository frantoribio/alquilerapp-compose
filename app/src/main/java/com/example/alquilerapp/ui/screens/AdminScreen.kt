package com.example.alquilerapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/**
 * función que muestra el panel de administrador
 */
@Composable
fun AdminScreen(onLogout: () -> Unit, modifier: Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Panel de Administrador")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogout) { // Llamada al callback cuando se presiona el botón
            Text("Cerrar Sesión")
        }
    }
}

/**
 * función que muestra el panel de propietario
 */
@Composable
fun PropietarioScreen(onLogout: () -> Unit, modifier: Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Panel de Propietario")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogout) { // Llamada al callback cuando se presiona el botón
            Text("Cerrar Sesión")
        }
    }
}

/**
 * función que muestra el panel de estudiante
 */
@Composable
fun EstudianteScreen(onLogout: () -> Unit, modifier: Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Panel de Estudiante")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogout) { // Llamada al callback cuando se presiona el botón
            Text("Cerrar Sesión")
        }
    }
}
