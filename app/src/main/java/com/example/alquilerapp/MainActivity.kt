package com.example.alquilerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alquilerapp.data.TokenStore
import com.example.alquilerapp.data.model.dto.UsuarioDTO
import com.example.alquilerapp.data.network.ApiServiceBuilder
import com.example.alquilerapp.repository.AlquilerRepository
import com.example.alquilerapp.repository.UsuarioRepository
import com.example.alquilerapp.ui.components.BottomBar
import com.example.alquilerapp.ui.screens.*
import com.example.alquilerapp.viewmodel.CreateRoomViewModelFactory
import com.example.alquilerapp.viewmodel.HabitacionesViewModel
import com.example.alquilerapp.viewmodel.LoginViewModel
import com.example.alquilerapp.viewmodel.PropietarioViewModelFactory
import com.example.alquilerapp.viewmodel.UsuariosViewModel
import com.example.alquilerapp.viewmodel.UsuariosViewModelFactory

/**
 * MainActivity principal que configura la navegación y el tema de la aplicación.
 */
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
                    val context = LocalContext.current
                    val tokenStore = remember { TokenStore(context) }
                    val apiService = remember { ApiServiceBuilder.create(tokenStore) }
                    val alquilerRepository = remember { AlquilerRepository(apiService) }
                    val createRoomFactory = remember {
                        CreateRoomViewModelFactory(alquilerRepository)
                    }
                    val propietarioFactory = remember {
                        PropietarioViewModelFactory(alquilerRepository)
                    }
                    val usuariosVM: UsuariosViewModel = viewModel(factory = UsuariosViewModelFactory(UsuarioRepository(apiService)))

                    var shouldRefresh by remember { mutableStateOf(false) }




                    NavHost(navController = navController, startDestination = "landing") {

                        composable("landing") {
                            LandingScreen(viewModel = habVM, onLoginClick = { navController.navigate("login") })
                        }

                        composable("login") {
                            Scaffold(
                                bottomBar = { BottomBar(navController) }
                            ) { padding ->
                                LoginScreen(
                                    viewModel = loginVM,
                                    onRoleNavigate = { role ->
                                        when (role.uppercase()) {
                                            "ADMIN" -> navController.navigate("admin") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                            "PROPIETARIO" -> navController.navigate("propietario") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                            "ALUMNO" -> navController.navigate("alumno") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        }
                                    },
                                    onNavigateToRegistro = { navController.navigate("registro") },
                                    modifier = Modifier.padding(padding)
                                )
                            }
                        }

                        composable("registro") {
                            Scaffold(bottomBar = { BottomBar(navController) }) { padding ->
                                RegistroScreen(
                                    registroViewModel = loginVM,
                                    navController = navController,
                                    modifier = Modifier.padding(padding),
                                    onNavigateBack = { navController.navigate("login") }
                                )
                            }
                        }

                        composable("admin") {
                            //val usuariosVM: UsuariosViewModel = viewModel(factory = UsuariosViewModelFactory(UsuarioRepository(apiService)))

                            Scaffold(bottomBar = { BottomBar(navController) }) { padding ->
                                UsuariosScreen(
                                    viewModel = usuariosVM,
                                    onCrearUsuario = { navController.navigate("usuarioForm") },
                                    onEditarUsuario = { usuario -> navController.navigate("usuarioForm?id=${usuario.id}") },
                                    onLogout = onLogout,
                                    modifier = Modifier.padding(padding)
                                )
                            }
                        }

                        composable("propietario") {
                            Scaffold(
                                bottomBar = { BottomBar(navController) }
                            ) { padding ->
                                PropietarioScreen(
                                    viewModel = viewModel(factory = propietarioFactory),
                                    onLogout = onLogout,
                                    // ASEGÚRATE DE PASAR LA FUNCIÓN DE NAVEGACIÓN AQUÍ
                                    onNavigateToCreateRoom = { navController.navigate("create_room_screen") },
                                    modifier = Modifier.padding(padding),
                                    shouldRefresh = shouldRefresh
                                )
                            }
                        }

                        composable("create_room_screen") {
                            CreateRoomScreen(
                                viewModel = viewModel(factory = createRoomFactory),
                                onRoomCreated = {
                                    // Vuelve a la pantalla anterior (PropietarioScreen) al completar
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("shouldRefresh", true)
                                    navController.popBackStack()
                                },
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("alumno") {
                            Scaffold(
                                bottomBar = { BottomBar(navController) }
                            ) { padding ->
                                EstudianteScreen(
                                    viewModel = habVM,
                                    onLogout = onLogout,
                                    onReservarClick = { idHabitacion ->
                                        // Aquí defines qué hacer cuando el estudiante reserva
                                        // Por ejemplo, navegar a una pantalla de confirmación:
                                        navController.navigate("reservaConfirmada/$idHabitacion")
                                    },
                                    modifier = Modifier.padding(padding)

                                )
                            }
                        }

                        composable("reservaConfirmada/{idHabitacion}") { backStackEntry ->
                            val idHabitacion = backStackEntry.arguments?.getString("idHabitacion")
                            ReservaConfirmadaScreen(
                                idHabitacion = idHabitacion,
                                onBack = { navController.popBackStack() }
                            )
                        }


                        composable("usuarioForm") {
                            /*val context = LocalContext.current
                            val tokenStore = TokenStore(context)
                            val apiService = ApiServiceBuilder.create(tokenStore)
                            val usuarioRepository = UsuarioRepository(apiService)
                            val factory = UsuariosViewModelFactory(usuarioRepository)
                            val usuariosVM: UsuariosViewModel = viewModel(factory = factory)*/



                            UsuarioFormScreen(

                                initialData = null, // o desde ViewModel si estás editando
                                //initialData = usuariosVM.usuarioSeleccionado as UsuarioDTO?,

                                onSubmit = { dto ->
                                    // lógica para crear o actualizar usuario
                                    navController.popBackStack()
                                },
                                onCancel = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
