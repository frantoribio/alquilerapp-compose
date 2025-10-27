package com.example.alquilerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alquilerapp.data.model.Usuario
import com.example.alquilerapp.data.model.dto.UsuarioDTO
import com.example.alquilerapp.viewmodel.UsuariosViewModel


@Composable
fun UsuariosScreen(
    viewModel: UsuariosViewModel,
    onCrearUsuario: () -> Unit,
    onEditarUsuario: (Usuario) -> Unit,
    onLogout: () -> Unit,
    modifier: Any
) {
    val usuarios = viewModel.usuarios
    val loading = viewModel.loading
    val error = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Gestión de Usuarios", style = MaterialTheme.typography.headlineMedium)

        if (loading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("Error: $error", color = Color.Red)
        } else {
            LazyColumn {
                items(usuarios) { usuario ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Nombre: ${usuario.nombre}")
                            Text("Email: ${usuario.email}")
                            Text("Rol: ${usuario.rol}")
                            Row {
                                Button(onClick = { viewModel.eliminarUsuario(usuario.id!!) }) {
                                    Text("Eliminar")
                                }
                                Spacer(Modifier.width(8.dp))
                                Button(onClick = { onEditarUsuario(usuario) }) {
                                    Text("Editar")
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = onCrearUsuario) {
            Text("Crear nuevo usuario")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onLogout) { // Llamada al callback cuando se presiona el botón
            Text("Cerrar Sesión")
        }
    }
}