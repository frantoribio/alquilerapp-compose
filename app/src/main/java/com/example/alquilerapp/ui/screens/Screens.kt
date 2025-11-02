package com.example.alquilerapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.alquilerapp.data.model.getEmulatedImageUrl
import com.example.alquilerapp.viewmodel.HabitacionesViewModel
import com.example.alquilerapp.viewmodel.PropietarioViewModel
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider

/**
 * función que muestra el panel de propietario
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropietarioScreen(
    onLogout: () -> Unit,
    onNavigateToCreateRoom: () -> Unit,
    viewModel: PropietarioViewModel = viewModel(), // Usa la inyección con factory en el MainActivity
    modifier: Modifier
) {
    val state = viewModel.habitaciones
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Mis Propiedades") },
                actions = @Composable {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Filled.Logout, contentDescription = "Cerrar sesión")
                    }


                })

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
        }
    }
}

// -------------------------------------------------------------
// Componente auxiliar para mostrar cada habitación
// -------------------------------------------------------------
@SuppressLint("DefaultLocale")
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteScreen(
    viewModel: HabitacionesViewModel,
    onLogout: () -> Unit,
    onReservarClick: (idHabitacion: String) -> Unit,
    modifier: Modifier = Modifier

) {
    val habitaciones by viewModel.habitaciones.collectAsState()

    // Cargar habitaciones al iniciar
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.loadHabitaciones()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Estudiante") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize()) {
            items(habitaciones) { hab ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = hab.getEmulatedImageUrl(),
                            contentDescription = "Imagen de ${hab.direccion}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(hab.direccion, style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(4.dp))
                        Text("Precio: €${hab.precioMensual} / mes")
                        Spacer(Modifier.height(8.dp))
                        Text(hab.descripcion)
                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = { onReservarClick(hab.id) },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Reservar")
                        }
                    }
                }

            }
            item {
                Spacer(
                    modifier = Modifier.height(
                        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 64.dp
                    )
                )
            }
        }
    }
}

/**
 * función que muestra el panel de confirmación de reserva
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaConfirmadaScreen(
    idHabitacion: String?,
    onBack: () -> Unit // función para volver atrás
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reserva Confirmada") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Reserva confirmada", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("ID de habitación: ${idHabitacion ?: "Desconocido"}")
            }
        }
    }
}