package com.grupo7.taskflow.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grupo7.taskflow.screen.SplashScreen
import com.grupo7.taskflow.screen.TaskDetailScreen
import com.grupo7.taskflow.screen.TaskListScreen
import com.grupo7.taskflow.viewmodel.TareaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: TareaViewModel = viewModel()

    // Cambiamos el destino inicial a "splash"
    NavHost(navController = navController, startDestination = "splash") {

        // Agregamos la ruta de la pantalla de carga
        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("lista_tareas") {
            TaskListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("detalle_tarea/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            TaskDetailScreen(
                navController = navController,
                viewModel = viewModel,
                tareaId = if (id == "nueva") null else id
            )
        }
    }
}