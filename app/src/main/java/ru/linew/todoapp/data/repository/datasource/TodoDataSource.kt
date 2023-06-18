package ru.linew.todoapp.data.repository.datasource

import ru.linew.todoapp.data.model.TodoItemDto

interface TodoDataSource {
    fun provideTodos(): List<TodoItemDto>
}