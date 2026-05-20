package com.grupo7.taskflow.model

import java.util.UUID


/* Data class final, solo admite datos sanitizados y validados previamente por el viewmodel
 * No contiene lógica de interfaz
 */
data class Tarea(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val descripcion: String = "",
    val estaCompleta: Boolean = false,
    val prioridad: Prioridad = Prioridad.MEDIA
)