package com.example.alquilerapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.alquilerapp.ui.navigation.BottomNavItem

@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            icon = { Icon(BottomNavItem.Home.icon, contentDescription = BottomNavItem.Home.label) },
            label = { Text(BottomNavItem.Home.label) },
            selected = false, // Puedes mejorar esto con currentBackStackEntry
            onClick = {
                navController.navigate(BottomNavItem.Home.route) {
                    popUpTo(0)
                    launchSingleTop = true
                }
            }
        )
    }
}

