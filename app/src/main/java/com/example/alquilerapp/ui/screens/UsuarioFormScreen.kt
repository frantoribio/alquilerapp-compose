package com.example.alquilerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.alquilerapp.data.model.dto.UsuarioDTO
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun UsuarioFormScreen(
    initialData: UsuarioDTO? = null,
    onSubmit: (UsuarioDTO) -> Unit,
    onCancel: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf(initialData?.nombre ?: "") }
    var email by rememberSaveable { mutableStateOf(initialData?.email ?: "") }
    var contraseña by rememberSaveable { mutableStateOf("") }
    var rol by rememberSaveable { mutableStateOf(initialData?.rol ?: "PROPIETARIO") }

    val esEdicion = initialData != null

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = if (esEdicion) "Editar Usuario" else "Crear Usuario",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        if (!esEdicion) {
            OutlinedTextField(
                value = contraseña,
                onValueChange = { contraseña = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Spacer(Modifier.height(8.dp))

        RolDropdown(rol) {
            val it = ""
            rol = it
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = {
                val dto = UsuarioDTO(
                    id = initialData?.id,
                    nombre = nombre,
                    email = email,
                    contraseña = if (!esEdicion) contraseña else null,
                    rol = rol
                )
                onSubmit(dto)
            }) {
                Text(if (esEdicion) "Actualizar" else "Crear")
            }

            Spacer(Modifier.width(8.dp))

            OutlinedButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RolDropdown(selectedRol: String, onRolSelected: (String) -> Unit) {
    val roles = listOf("PROPIETARIO", "ALUMNO", "ADMIN")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedRol,
            onValueChange = {},
            readOnly = true,
            label = { Text("Rol") },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor() // 👈 necesario para posicionar el menú
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            roles.forEach { rol ->
                DropdownMenuItem(
                    text = { Text(rol) },
                    onClick = {
                        onRolSelected(rol)
                        expanded = false
                    }
                )
            }
        }
    }
}

