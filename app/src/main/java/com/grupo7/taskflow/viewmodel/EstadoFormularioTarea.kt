package com.grupo7.taskflow.viewmodel

import com.grupo7.taskflow.model.Prioridad

/* Data class que es un estado intermedio, es mas leniente con las validaciones
 * Todos los datos que pasan por este data class son validados al presionar el botón de guardado
 */
data class EstadoFormularioTarea(
    val id: String? = null,
    val titulo: String = "",
    val descripcion: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val completada: Boolean = false,
    val errorValidacion: Boolean = false
)