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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alquilerapp.data.model.Usuario
import com.example.alquilerapp.viewmodel.UsuariosViewModel
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun UsuariosScreen(
    viewModel: UsuariosViewModel,
    onCrearUsuario: () -> Unit,
    onEditarUsuario: (Usuario) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val usuarios = viewModel.usuarios
    val loading = viewModel.loading
    val error = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text("Gestión de Usuarios", style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión"
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        Button(onClick = onCrearUsuario) {
            Text("Crear nuevo usuario")
        }

        if (loading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("Error: $error", color = Color.Red)
        } else {
            Spacer(Modifier.height(16.dp))
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
            ) {
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
}