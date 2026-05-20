package com.grupo7.taskflow.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grupo7.taskflow.model.Prioridad
import com.grupo7.taskflow.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navController: NavController,
    viewModel: TareaViewModel,
    tareaId: String?
) {
    LaunchedEffect(tareaId) {
        viewModel.cargarTareaEnFormulario(tareaId)
    }

    val estadoFormulario by viewModel.estadoFormulario.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (tareaId == null) "Nueva Tarea" else "Detalle de Tarea") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Información de la Tarea",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = estadoFormulario.id ?: "Generación Automática",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("ID") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Tag, contentDescription = null) },
                        singleLine = true,
                        enabled = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = estadoFormulario.titulo,
                        onValueChange = { viewModel.actualizarTitulo(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Título") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) },
                        isError = estadoFormulario.errorValidacion,
                        singleLine = true
                    )
                    if (estadoFormulario.errorValidacion) {
                        Text(
                            text = "El título es obligatorio",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = estadoFormulario.descripcion,
                        onValueChange = { viewModel.actualizarDescripcion(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Descripción (Opcional)") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Subject, contentDescription = null) },
                        minLines = 3
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Prioridad", style = MaterialTheme.typography.labelLarge)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Prioridad.entries.forEach { prioridad ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = estadoFormulario.prioridad == prioridad,
                                    onClick = { viewModel.actualizarPrioridad(prioridad) }
                                )
                                Text(prioridad.name)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Completada")
                            }
                            Switch(
                                checked = estadoFormulario.completada,
                                onCheckedChange = { viewModel.alternarCompletada(it) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cancelar")
                        }

                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val guardadoExitoso = viewModel.guardarTarea()
                                if (guardadoExitoso) {
                                    navController.popBackStack()
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}