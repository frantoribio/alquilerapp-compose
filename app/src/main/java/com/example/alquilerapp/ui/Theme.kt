package com.example.alquilerapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme // Usamos ColorScheme para Material 3
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 1. Definición del ColorScheme Oscuro para Material 3
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),      // Púrpura, común en temas oscuros
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC5),
    // Puedes añadir más colores aquí (tertiary, background, surface, etc.)
)

// 2. Función Composable del Tema
/**
 * Composable que define el tema de la aplicación.
 *
 */
@Composable
fun AlquilerTheme(
    darkTheme: Boolean = true, // Opcional, para permitir cambiar el tema
    content: @Composable () -> Unit
) {
    // Definir el esquema de color a usar (aquí solo usamos el oscuro)
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme // Si tuvieras un LightColorScheme, iría aquí
    }

    // Aplicar el tema de Material 3
    MaterialTheme(
        colorScheme = colorScheme,
        // Aquí puedes añadir typography y shapes si los defines
        content = content
    )
}