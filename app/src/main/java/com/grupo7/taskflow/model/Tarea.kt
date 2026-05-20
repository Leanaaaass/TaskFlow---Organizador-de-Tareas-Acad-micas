package com.grupo7.taskflow.model

import java.util.UUID

data class Tarea(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val descripcion: String = "",
    val estaCompleta: Boolean = false,
    val prioridad: Prioridad = Prioridad.MEDIA
)