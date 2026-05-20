package com.grupo7.taskflow.repository

import com.grupo7.taskflow.model.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskRepository {

    private val _tasks = MutableStateFlow<List<Tarea>>(emptyList())
    val tasks: StateFlow<List<Tarea>> = _tasks.asStateFlow()

    fun addTask(task: Tarea) {
        _tasks.update { currentTasks ->
            currentTasks + task
        }
    }

    fun updateTask(updatedTask: Tarea) {
        _tasks.update { currentTasks ->
            currentTasks.map { task ->
                if (task.id == updatedTask.id) updatedTask else task
            }
        }
    }

    fun deleteTask(taskId: String) {
        _tasks.update { currentTasks ->
            currentTasks.filter { it.id != taskId }
        }
    }

    fun getTaskById(taskId: String): Tarea? {
        return _tasks.value.find { it.id == taskId }
    }
}