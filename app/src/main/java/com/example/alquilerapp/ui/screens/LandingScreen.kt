package com.example.alquilerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alquilerapp.viewmodel.HabitacionesViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.example.alquilerapp.data.model.getEmulatedImageUrl

/**
 * Composable para la pantalla inicial.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(viewModel: HabitacionesViewModel, onLoginClick: () -> Unit) {
    val habitaciones by viewModel.habitaciones.collectAsState()
    // Load once
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.loadHabitaciones()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alquiler de Habitaciones") },
                actions = {
                    IconButton(onClick = onLoginClick) {
                        Icon(Icons.Default.Person, contentDescription = "Login")
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
                                .height(200.dp), // Altura fija para la imagen
                            contentScale = ContentScale.Crop // Recorta la imagen para llenar el espacio
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(hab.direccion, style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(4.dp))
                        Text("Precio: ${'€'}${hab.precioMensual} €/mes")
                        Spacer(Modifier.height(8.dp))
                        Text(hab.descripcion)
                    }
                }
            }
        }
    }
}
