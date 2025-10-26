package com.example.alquilerapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * clase sellada para los items de la barra inferior
 *
 */
sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("landing", Icons.Filled.Home, "Inicio")
}