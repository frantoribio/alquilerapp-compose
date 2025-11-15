package com.example.alquilerapp.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.alquilerapp.viewmodel.CreateRoomViewModel
import java.util.UUID

@Composable
fun EditarHabitacionScreen(
    habitacionId: UUID,
    viewModel: CreateRoomViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val habitacion by remember { mutableStateOf(viewModel.habitacionActual) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(habitacionId) {
        viewModel.cargarHabitacionPorId(habitacionId)
    }

    habitacion?.let { habitacion ->
        HabitacionForm(
            initialData = habitacion,
            onSubmit = { actualizada ->
                viewModel.actualizarHabitacion(actualizada)
                onBack()
            },
            onCancel = onBack,
            onDelete = { showDeleteDialog = true }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Eliminar habitación?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarHabitacion(habitacionId)
                    showDeleteDialog = false
                    onBack()
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}