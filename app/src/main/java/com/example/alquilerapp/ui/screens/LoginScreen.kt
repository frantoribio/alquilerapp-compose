package com.example.alquilerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.alquilerapp.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onRoleNavigate: (String) -> Unit,
    onNavigateToRegistro: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val role by viewModel.role.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var error by remember { mutableStateOf("") }

    LaunchedEffect(role) {
        role?.let { onRoleNavigate(it) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                error = ""
            },
            label = { Text("Email") },
            singleLine = true
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                error = ""
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        if (error.isNotBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    error = "Email y contraseña son obligatorios"
                } else {
                    viewModel.login(email, password)
                }
            },
            enabled = !loading
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
            }
            Text("Entrar")
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onNavigateToRegistro) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}