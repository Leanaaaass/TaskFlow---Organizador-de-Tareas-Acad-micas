package com.grupo7.taskflow.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000) // Temporizador de 2 segundos

        // Navega a la lista y elimina el splash del historial de navegación
        navController.navigate("lista_tareas") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp), // Un poco de margen a los lados
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Organizador de Tareas", // O puedes poner "TaskFlow\nOrganizador de Tareas"
            style = MaterialTheme.typography.headlineLarge, // Letra un poco más adaptable
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center // Centra el texto si ocupa dos líneas
        )
    }
}