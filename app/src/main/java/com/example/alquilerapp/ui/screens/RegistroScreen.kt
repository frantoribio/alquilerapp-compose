package com.example.alquilerapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistroConfirmado: (email: String, password: String, rol: String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val roles = listOf("PROPIETARIO", "ALUMNO")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro de Usuario", style = MaterialTheme.typography.headlineSmall)
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

        Spacer(Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = rolSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Rol") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                roles.forEach { rol ->
                    DropdownMenuItem(
                        text = { Text(rol) },
                        onClick = {
                            rolSeleccionado = rol
                            expanded = false
                            error = ""
                        }
                    )
                }
            }
        }

        if (error.isNotBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() || rolSeleccionado.isBlank()) {
                    error = "Todos los campos son obligatorios"
                } else {
                    onRegistroConfirmado(email, password, rolSeleccionado)
                }
            }
        ) {
            Text("Registrar")
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onNavigateBack) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}