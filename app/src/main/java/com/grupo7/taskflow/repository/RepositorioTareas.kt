package com.grupo7.taskflow.repository

import com.grupo7.taskflow.model.Prioridad
import com.grupo7.taskflow.model.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RepositorioTareas {

    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    fun anadirTarea(tarea: Tarea) {
        _tareas.update { tareasActuales ->
            tareasActuales + tarea
        }
    }

    fun actualizarTarea(tareaActualizada: Tarea) {
        _tareas.update { tareasActuales ->
            tareasActuales.map { tarea ->
                if (tarea.id == tareaActualizada.id) tareaActualizada else tarea
            }
        }
    }

    fun borrarTarea(id: String) {
        _tareas.update { tareasActuales ->
            tareasActuales.filter { it.id != id }
        }
    }

    fun obtenerTareaPorID(id: String): Tarea? {
        return _tareas.value.find { it.id == id }
    }

    fun obtenerTareasPendientes(): List<Tarea> {
        return _tareas.value.filter { !it.estaCompleta }
    }

    fun obtenerTareasPorPrioridad(prioridad: Prioridad): List<Tarea> {
        return _tareas.value.filter { it.prioridad == prioridad }
    }

    fun alternarEstadoCompletado(id: String) {
        _tareas.update { tareasActuales ->
            tareasActuales.map { tarea ->
                if (tarea.id == id) tarea.copy(estaCompleta = !tarea.estaCompleta) else tarea
            }
        }
    }
}