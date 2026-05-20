package com.grupo7.taskflow.viewmodel

import androidx.lifecycle.ViewModel
import com.grupo7.taskflow.model.Prioridad
import com.grupo7.taskflow.model.Tarea
import com.grupo7.taskflow.repository.RepositorioTareas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TareaViewModel(
    private val repositorio: RepositorioTareas = RepositorioTareas()
) : ViewModel() {

    val tareas: StateFlow<List<Tarea>> = repositorio.tareas

    private val _estadoFormulario = MutableStateFlow(EstadoFormularioTarea())
    val estadoFormulario: StateFlow<EstadoFormularioTarea> = _estadoFormulario.asStateFlow()

    fun actualizarTitulo(nuevoTitulo: String) {
        _estadoFormulario.update {
            it.copy(titulo = nuevoTitulo, errorValidacion = false)
        }
    }

    fun actualizarDescripcion(nuevaDescripcion: String) {
        _estadoFormulario.update {
            it.copy(descripcion = nuevaDescripcion)
        }
    }

    fun actualizarPrioridad(nuevaPrioridad: Prioridad) {
        _estadoFormulario.update {
            it.copy(prioridad = nuevaPrioridad)
        }
    }

    fun alternarCompletada(completada: Boolean) {
        _estadoFormulario.update {
            it.copy(completada = completada)
        }
    }

    fun cargarTareaEnFormulario(id: String?) {
        if (id == null) {
            _estadoFormulario.value = EstadoFormularioTarea()
            return
        }

        val tareaExistente = repositorio.obtenerTareaPorID(id)
        if (tareaExistente != null) {
            _estadoFormulario.value = EstadoFormularioTarea(
                id = tareaExistente.id,
                titulo = tareaExistente.titulo,
                descripcion = tareaExistente.descripcion,
                prioridad = tareaExistente.prioridad,
                completada = tareaExistente.estaCompleta,
                errorValidacion = false
            )
        }
    }

    fun guardarTarea(): Boolean {
        val estadoActual = _estadoFormulario.value

        if (estadoActual.titulo.isBlank()) {
            _estadoFormulario.update { it.copy(errorValidacion = true) }
            return false
        }

        if (estadoActual.id == null) {
            val nuevaTarea = Tarea(
                titulo = estadoActual.titulo,
                descripcion = estadoActual.descripcion,
                prioridad = estadoActual.prioridad,
                estaCompleta = estadoActual.completada
            )
            repositorio.anadirTarea(nuevaTarea)
        } else {
            val tareaActualizada = Tarea(
                id = estadoActual.id,
                titulo = estadoActual.titulo,
                descripcion = estadoActual.descripcion,
                prioridad = estadoActual.prioridad,
                estaCompleta = estadoActual.completada
            )
            repositorio.actualizarTarea(tareaActualizada)
        }

        _estadoFormulario.value = EstadoFormularioTarea()
        return true
    }

    fun alternarEstadoTareaLista(id: String) {
        repositorio.alternarEstadoCompletado(id)
    }

    fun eliminarTarea(id: String) {
        repositorio.borrarTarea(id)
    }
}