package com.example.alquilerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alquilerapp.ui.screens.*
import com.example.alquilerapp.viewmodel.HabitacionesViewModel
import com.example.alquilerapp.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    val habVM: HabitacionesViewModel = viewModel()
                    val loginVM: LoginViewModel = viewModel()
                    val onLogout: () -> Unit = {
                        loginVM.logout() // 1. Llama al ViewModel para limpiar la sesión
                        navController.navigate("login") {
                            // 2. Navega a 'login' y limpia TODA la pila para que no haya vuelta atrás
                            popUpTo("landing") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    NavHost(navController = navController, startDestination = "landing") {
                        composable("landing") {
                            LandingScreen(viewModel = habVM, onLoginClick = { navController.navigate("login") })
                        }
                        composable("login") {
                            LoginScreen(viewModel = loginVM) { role ->
                                when (role.uppercase()) {
                                    "ADMIN" -> navController.navigate("admin") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                    "PROPIETARIO" -> navController.navigate("propietario") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                    "ESTUDIANTE" -> navController.navigate("estudiante") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                    else -> { /* ignore */ }
                                }
                            }
                        }
                        composable("admin") { AdminScreen(onLogout = onLogout) }
                        composable("propietario") { PropietarioScreen(onLogout = onLogout) }
                        composable("estudiante") { EstudianteScreen(onLogout = onLogout) }
                    }
                }
            }
        }
    }
}
