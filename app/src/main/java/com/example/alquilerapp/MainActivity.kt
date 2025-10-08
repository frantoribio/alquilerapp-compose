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

                    NavHost(navController = navController, startDestination = "landing") {
                        composable("landing") {
                            LandingScreen(viewModel = habVM, onLoginClick = { navController.navigate("login") })
                        }
                        composable("login") {
                            LoginScreen(viewModel = loginVM) { role ->
                                when (role.uppercase()) {
                                    "ADMIN" -> navController.navigate("admin")
                                    "PROPIETARIO" -> navController.navigate("propietario")
                                    "ESTUDIANTE" -> navController.navigate("estudiante")
                                    else -> { /* ignore */ }
                                }
                            }
                        }
                        composable("admin") { AdminScreen() }
                        composable("propietario") { PropietarioScreen() }
                        composable("estudiante") { EstudianteScreen() }
                    }
                }
            }
        }
    }
}
