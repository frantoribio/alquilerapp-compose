package com.example.alquilerapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alquilerapp.viewmodel.PropietarioViewModel

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
/*
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
*/
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropietarioScreen(
    onLogout: () -> Unit,
    // AÑADIDO: Callback para navegar a la pantalla de creación
    onNavigateToCreateRoom: () -> Unit,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Panel de Propietario") }
                // Puedes añadir acciones como un botón de logout aquí si prefieres
            )
        },
        // Botón flotante para la acción principal: CREAR HABITACIÓN
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateRoom) {
                Icon(Icons.Filled.Add, contentDescription = "Crear nueva habitación")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Importante para aplicar el padding del Scaffold
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Empezamos desde arriba
        ) {

            // --- Sección para Listar Habitaciones ---
            Text(
                text = "Mis Habitaciones (Pendiente de implementar lista real)",
                modifier = Modifier.padding(16.dp)
            )

            // Aquí iría un LazyColumn o una lista real de Habitaciones
            // Ejemplo: HabitacionesList(viewModel.propietarioHabitaciones)


            // Dejamos el botón de cerrar sesión en la parte inferior o en el menú
            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onLogout) {
                Text("Cerrar Sesión")
            }
        }
    }
}
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropietarioScreen(
    onLogout: () -> Unit,
    onNavigateToCreateRoom: () -> Unit,
    // AÑADIDO: Recibe el ViewModel
    viewModel: PropietarioViewModel = viewModel(), // Usa la inyección con factory en el MainActivity
    modifier: Modifier
) {
    val state = viewModel.habitaciones
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Mis Propiedades") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateRoom) {
                Icon(Icons.Filled.Add, contentDescription = "Crear nueva habitación")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // 1. Mostrar estado de carga o error
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(24.dp))
            } else if (errorMessage != null) {
                Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                // Opción: Añadir botón para reintentar la carga
                Button(onClick = viewModel::cargarHabitacionesPropietario) {
                    Text("Reintentar")
                }
            } else if (state.isEmpty()) {
                Text("No tienes habitaciones listadas. ¡Crea una!", modifier = Modifier.padding(24.dp))
            }

            // 2. LISTA DE HABITACIONES (LazyColumn)
            if (state.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxSize(), // Ocupa el espacio restante
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text("Habitaciones Listadas:", style = MaterialTheme.typography.titleMedium)
                        Divider(Modifier.padding(vertical = 8.dp))
                    }
                    items(state) { habitacion ->
                        HabitacionListItem(habitacion = habitacion) // <-- Composable para cada elemento
                    }
                }
            }

            // 3. Botón de Cerrar Sesión (al final)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onLogout) {
                Text("Cerrar Sesión")
            }
        }
    }
}

// -------------------------------------------------------------
// Componente auxiliar para mostrar cada habitación
// -------------------------------------------------------------
@Composable
fun HabitacionListItem(habitacion: com.example.alquilerapp.data.model.Habitacion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Navegar a detalles */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(habitacion.titulo, style = MaterialTheme.typography.titleLarge)
            Text("${habitacion.ciudad} - ${habitacion.direccion}")
            Text("Precio: ${String.format("%.2f", habitacion.precioMensual)}€/mes",
                style = MaterialTheme.typography.bodyMedium)
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
