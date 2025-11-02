package com.example.alquilerapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alquilerapp.viewmodel.CreateRoomViewModel

/**
 * Composable para la creación de una nueva habitación.
 *
 * @param viewModel El ViewModel asociado a esta pantalla.
 * @param onRoomCreated Función para volver atrás al éxito.
 * @param onBack Función para volver atrás.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomScreen(
    viewModel: CreateRoomViewModel = viewModel(),
    onRoomCreated: () -> Unit, // Función para volver atrás al éxito
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val isSaving = viewModel.isSaving

    // Observar el estado de éxito
    LaunchedEffect(viewModel.saveSuccess) {
        if (viewModel.saveSuccess) {
            Toast.makeText(context, "Habitación creada con éxito", Toast.LENGTH_SHORT).show()
            onRoomCreated()
        }
    }

    // --- Lógica para habilitar el botón de Guardar ---
    val isFormValid = remember(
        viewModel.roomTitle, viewModel.roomCity, viewModel.roomAddress, viewModel.roomPrice
    ) {
        // Validar campos obligatorios del DTO
        viewModel.roomTitle.isNotBlank() &&
                viewModel.roomCity.isNotBlank() &&
                viewModel.roomAddress.isNotBlank() &&
                viewModel.roomPrice.toDoubleOrNull() != null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Habitación") },
                navigationIcon = {
                    IconButton(onClick = onBack, enabled = !isSaving) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Añadir scroll
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Campo Título (Usando roomTitle y onTitleChange)
            OutlinedTextField(
                value = viewModel.roomTitle,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Título del Anuncio (*)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 2. Campo Ciudad
            OutlinedTextField(
                value = viewModel.roomCity,
                onValueChange = viewModel::onCityChange,
                label = { Text("Ciudad (*)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 3. Campo Dirección
            OutlinedTextField(
                value = viewModel.roomAddress,
                onValueChange = viewModel::onAddressChange,
                label = { Text("Dirección de la Propiedad (*)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            )
            Spacer(modifier = Modifier.height(16.dp))


            // 4. Campo Precio Mensual (Usando roomPrice y onPriceChange)
            OutlinedTextField(
                value = viewModel.roomPrice,
                onValueChange = viewModel::onPriceChange,
                label = { Text("Precio Mensual (*)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Text("€") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 5. Campo Descripción (Usando roomDescription y onDescriptionChange)
            OutlinedTextField(
                value = viewModel.roomDescription,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Descripción detallada") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 3,
                maxLines = 5,
                enabled = !isSaving
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 6. Campo URL de Imagen
            OutlinedTextField(
                value = viewModel.imageUrl,
                onValueChange = viewModel::onImageUrlChange,
                label = { Text("URL de la imagen principal") },
                placeholder = { Text("Ej: http://tuapi.com/imagen.jpg") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            )
            Spacer(modifier = Modifier.height(24.dp))


            // Botón de Guardar
            Button(
                onClick = viewModel::createRoom,
                enabled = !isSaving && isFormValid, // Condición de guardado combinada
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Habitación")
                }
            }

            // Mensaje de Error
            viewModel.errorMessage?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}