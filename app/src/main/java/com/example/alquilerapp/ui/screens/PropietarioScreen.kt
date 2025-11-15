package com.example.alquilerapp.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.alquilerapp.data.model.getEmulatedImageUrl
import com.example.alquilerapp.viewmodel.PropietarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropietarioScreen(
    onLogout: () -> Unit,
    onNavigateToCreateRoom: () -> Unit,
    //shouldRefresh: Boolean,
    viewModel: PropietarioViewModel = viewModel(), // Usa la inyección con factory en el MainActivity
    modifier: Modifier,

    navController: NavController
) {
    val state = viewModel.habitaciones
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val shouldRefresh = savedStateHandle?.get<Boolean>("shouldRefresh") == true

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            viewModel.cargarHabitacionesPropietario()
            savedStateHandle?.remove<Boolean>("shouldRefresh")
        }
    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Mis Propiedades") },
                actions = @Composable {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Filled.Logout, contentDescription = "Cerrar sesión")
                    }
                }
            )
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
// Componente auxiliar para mostrar cada habitación del propietario
// -------------------------------------------------------------
@SuppressLint("DefaultLocale")
@Composable
fun HabitacionListItem(habitacion: com.example.alquilerapp.data.model.Habitacion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Navegar a detalles */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Imagen
            habitacion.getEmulatedImageUrl()?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Imagen de la habitación",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }


            Text(habitacion.titulo, style = MaterialTheme.typography.titleLarge)
            Text("${habitacion.ciudad} - ${habitacion.direccion}")
            Text("Precio: ${String.format("%.2f", habitacion.precioMensual)}€/mes",
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}
