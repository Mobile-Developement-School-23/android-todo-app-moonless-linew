package ru.linew.todoapp.presentation.background.repository

import ru.linew.todoapp.presentation.model.TodoItem

interface DeadlineTodoProvider {
    suspend fun getDeadlineTodoList(todayMillis: Long): List<TodoItem>
}